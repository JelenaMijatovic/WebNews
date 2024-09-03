package raf.vp.jmijatovic11421rn.repositories.category;

import raf.vp.jmijatovic11421rn.entities.Article;
import raf.vp.jmijatovic11421rn.entities.Category;
import raf.vp.jmijatovic11421rn.repositories.MySQLAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLCategoryRepository extends MySQLAbstractRepository implements CategoryRepository {

    @Override
    public List<Category> getAllCategories() {

        List<Category> categories = new ArrayList<>();
        List<Integer> articles = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM categories");

            while (resultSet.next()) {
                articles = getArticles(resultSet.getString("categoryTitle"));
                categories.add(new Category(resultSet.getString("categoryTitle"), resultSet.getString("categoryDescription"), articles));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return categories;
    }

    @Override
    public List<Category> getCategoriesByPage(Integer page) {

        List<Category> categories = new ArrayList<>();
        List<Integer> articles = new ArrayList<>();
        int i = 0; int j = page*5;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM categories");

            while (resultSet.next()) {
                i++;
                if (i > j-5) {
                    if (i > j) {
                        break;
                    }
                    categories.add(new Category(resultSet.getString("categoryTitle"), resultSet.getString("categoryDescription"), articles));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return categories;
    }

    @Override
    public Category getCategory(String title) {
        Category category = null;
        List<Integer> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM categories where categoryTitle = ?");
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String catTitle = resultSet.getString("categoryTitle");
                String description = resultSet.getString("categoryDescription");
                category = new Category(catTitle, description, articles);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return category;
    }

    @Override
    public void editCategory(String title, Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE categories SET categoryTitle = ?, categoryDescription = ? where categoryTitle = ?");
            preparedStatement.setString(1, category.getCategoryTitle());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setString(3, title);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void addCategory(Category category) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO categories (categoryTitle, categoryDescription) VALUES(?, ?)");
            preparedStatement.setString(1, category.getCategoryTitle());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void deleteCategory(String title) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM categories where categoryTitle = ?");
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    private List<Integer> getArticles(String category) {
        List<Integer> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT articleId FROM articles where category = ?");
            preparedStatement.setString(1, category);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                articles.add(resultSet.getInt("articleId"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }
}
