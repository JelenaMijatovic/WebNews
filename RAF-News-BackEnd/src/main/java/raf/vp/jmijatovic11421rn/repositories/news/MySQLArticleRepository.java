package raf.vp.jmijatovic11421rn.repositories.news;

import raf.vp.jmijatovic11421rn.entities.*;
import raf.vp.jmijatovic11421rn.repositories.MySQLAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLArticleRepository extends MySQLAbstractRepository implements ArticleRepository {

    @Override
    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        User author = null;
        List<Comment> comments = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM articles");

            while (resultSet.next()) {

                author = getAuthor(resultSet.getString("articleAuthor"));

                articles.add(new Article(resultSet.getInt("articleId"), resultSet.getString("articleTitle"), resultSet.getString("articleText"), resultSet.getDate("creationDate"), resultSet.getInt("visitCount"), author, comments, tags, resultSet.getString("category")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }

    @Override
    public List<Article> getArticlesByPage(Integer page) {
        List<Article> articles = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        User author = null;
        List<Comment> comments = new ArrayList<>();
        int i = 0; int j = page*5;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM articles order by creationDate desc");

            while (resultSet.next()) {
                i++;
                if (i > j-5) {
                    if (i > j) {
                        break;
                    }

                    author = getAuthor(resultSet.getString("articleAuthor"));

                    articles.add(new Article(resultSet.getInt("articleId"), resultSet.getString("articleTitle"), resultSet.getString("articleText"), resultSet.getDate("creationDate"), resultSet.getInt("visitCount"), author, comments, tags, resultSet.getString("category")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }

    @Override
    public List<Article> getArticlesByPageAndCategory(String title, Integer page) {
        List<Article> articles = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        User author = null;
        List<Comment> comments = new ArrayList<>();
        int i = 0; int j = page*5;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM articles where category = ? order by creationDate desc");
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                i++;
                if (i > j-5) {
                    if (i > j) {
                        break;
                    }
                    author = getAuthor(resultSet.getString("articleAuthor"));
                    articles.add(new Article(resultSet.getInt("articleId"), resultSet.getString("articleTitle"), resultSet.getString("articleText"), resultSet.getDate("creationDate"), resultSet.getInt("visitCount"), author, comments, tags, resultSet.getString("category")));
                }
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

    @Override
    public List<Article> getArticlesByString(String search, Integer page) {
        List<Article> articles = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        User author = null;
        List<Comment> comments = new ArrayList<>();
        int i = 0; int j = page*5;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM articles where articleTitle like CONCAT('%',?,'%') or articleText like CONCAT('%',?,'%') order by creationDate desc");
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                i++;
                if (i > j-5) {
                    if (i > j) {
                        break;
                    }
                    author = getAuthor(resultSet.getString("articleAuthor"));
                    articles.add(new Article(resultSet.getInt("articleId"), resultSet.getString("articleTitle"), resultSet.getString("articleText"), resultSet.getDate("creationDate"), resultSet.getInt("visitCount"), author, comments, tags, resultSet.getString("category")));
                }
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

    @Override
    public Article getArticle(Integer id) {
        Article article = null;
        List<Tag> tags = new ArrayList<>();
        User author = null;
        List<Comment> comments = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM articles where articleId = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {

                tags = getTags(resultSet.getInt("articleId"));

                author = getAuthor(resultSet.getString("articleAuthor"));

                comments = getComments(resultSet.getInt("articleId"), 1);

                article = new Article(resultSet.getInt("articleId"), resultSet.getString("articleTitle"), resultSet.getString("articleText"), resultSet.getDate("creationDate"), resultSet.getInt("visitCount"), author, comments, tags, resultSet.getString("category"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return article;
    }

    @Override
    public void editArticle(Integer id, Article article) {
        List<String> existingTags = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE articles SET articleTitle = ?, articleText = ?, category = ? where articleId = ?");
            preparedStatement.setString(1, article.getArticleTitle());
            preparedStatement.setString(2, article.getArticleText());
            preparedStatement.setString(3, article.getCategory());
            preparedStatement.setInt(4, article.getArticleId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("UPDATE articles_of_category SET category = ? where articleId = ?");
            preparedStatement.setString(1, article.getCategory());
            preparedStatement.setInt(2, article.getArticleId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT tagName FROM tags_of_article where articleId = ?");
            preparedStatement.setInt(1, article.getArticleId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                existingTags.add(resultSet.getString("tagName"));
            }

            for (Tag tag : article.getTags()) {
                if (!existingTags.contains(tag.getTagName())) {
                    preparedStatement = connection.prepareStatement("INSERT INTO tags_of_article (articleId, tagName) VALUES(?, ?)");
                    preparedStatement.setInt(1, article.getArticleId());
                    preparedStatement.setString(2, tag.getTagName());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
    }

    @Override
    public void addArticle(Article article) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"articleId"};
            java.util.Date origDate = article.getCreationDate();
            int year = origDate.getYear();
            int month = origDate.getMonth();
            int day = origDate.getDay();
            Date date = new Date(year, month, day);
            preparedStatement = connection.prepareStatement("INSERT INTO articles (articleTitle, articleText, creationDate, visitCount, articleAuthor, category) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, article.getArticleTitle());
            preparedStatement.setString(2, article.getArticleText());
            preparedStatement.setDate(3, date);
            preparedStatement.setInt(4, article.getVisitCount());
            preparedStatement.setString(5, article.getArticleAuthor().getEmail());
            preparedStatement.setString(6, article.getCategory());
            preparedStatement.executeUpdate();
            System.out.println(article.getTags());
            List<Article> articles = getAllArticles();
            for (Tag tag : article.getTags()) {
                preparedStatement = connection.prepareStatement("INSERT INTO tags_of_article (articleId, tagName) VALUES(?, ?)");
                preparedStatement.setInt(1, articles.size());
                preparedStatement.setString(2, tag.getTagName());
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void deleteArticle(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM articles where articleId = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            getComments(id, 0);
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    private User getAuthor(String email) {
        User author = null;
        ResultSet resultAuthor = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users where email = ?");
            preparedStatement.setString(1, email);
            resultAuthor = preparedStatement.executeQuery();

            if(resultAuthor.next()) {
                author = new User(resultAuthor.getString("email"), resultAuthor.getString("firstName"), resultAuthor.getString("lastName"), resultAuthor.getString("userType"), resultAuthor.getBoolean("status"), resultAuthor.getString("password"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultAuthor);
            this.closeConnection(connection);
        }

        return author;
    }

    private List<Tag> getTags(Integer id) {
        List<Tag> tags = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ResultSet resultTags = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT tagName FROM tags_of_article where articleId = ?");
            preparedStatement.setInt(1, id);
            resultTags = preparedStatement.executeQuery();
            while (resultTags.next()) {
                tags.add(new Tag(resultTags.getString("tagName")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultTags);
            this.closeConnection(connection);
        }
        return tags;
    }

    private List<Comment> getComments(Integer id, Integer k) {
        List<Comment> comments = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ResultSet resultComments = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT commentId FROM comments_of_article where articleId = ?");
            preparedStatement.setInt(1, id);
            resultComments = preparedStatement.executeQuery();
            while (resultComments.next()) {
                if (k == 1) {
                    comments.add(getComment(resultComments.getInt("commentId")));
                } else {
                    deleteComment(resultComments.getInt("commentId"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultComments);
            this.closeConnection(connection);
        }
        return comments;
    }

    private Comment deleteComment(Integer id) {
        Comment comment = null;
        ResultSet resultComment = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM comments where commentId = ?");
            preparedStatement.setInt(1, id);
            resultComment = preparedStatement.executeQuery();

            if(resultComment.next()) {
                comment = new Comment(resultComment.getInt("commentId"), getAuthor(resultComment.getString("commentAuthor")), resultComment.getString("commentText"), resultComment.getDate("commentDate"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultComment);
            this.closeConnection(connection);
        }

        return comment;
    }

    private Comment getComment(Integer id) {
        Comment comment = null;
        ResultSet resultComment = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM comments where commentId = ?");
            preparedStatement.setInt(1, id);
            resultComment = preparedStatement.executeQuery();

            if(resultComment.next()) {
                comment = new Comment(resultComment.getInt("commentId"), getAuthor(resultComment.getString("commentAuthor")), resultComment.getString("commentText"), resultComment.getDate("commentDate"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultComment);
            this.closeConnection(connection);
        }

        return comment;
    }
}
