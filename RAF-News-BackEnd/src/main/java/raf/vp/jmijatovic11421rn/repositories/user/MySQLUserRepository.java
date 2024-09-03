package raf.vp.jmijatovic11421rn.repositories.user;

import org.apache.commons.codec.digest.DigestUtils;
import raf.vp.jmijatovic11421rn.entities.Category;
import raf.vp.jmijatovic11421rn.entities.User;
import raf.vp.jmijatovic11421rn.repositories.MySQLAbstractRepository;

import javax.inject.Named;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLUserRepository extends MySQLAbstractRepository implements UserRepository{

    @Override
    public User findUser(String email) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = new User(resultSet.getString("email"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("userType"), resultSet.getBoolean("status"), resultSet.getString("password"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                users.add(new User(resultSet.getString("email"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("userType"), resultSet.getBoolean("status"), resultSet.getString("password")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return users;
    }

    @Override
    public List<User> getUsersByPage(Integer page) {
        List<User> users = new ArrayList<>();

        int i = 0; int j = page*5;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                i++;
                if (i > j-5) {
                    if (i > j) {
                        break;
                    }
                    users.add(new User(resultSet.getString("email"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("userType"), resultSet.getBoolean("status"), resultSet.getString("password")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return users;
    }

    @Override
    public void editUser(String email, User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE users SET email = ?, firstName = ?, lastName = ?, userType = ? where email = ?");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getUserType());
            preparedStatement.setString(5, email);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO users (email, firstName, lastName, userType, status, password) VALUES(?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getUserType());
            preparedStatement.setBoolean(5, user.isActive());
            preparedStatement.setString(6, DigestUtils.sha256Hex(user.getPassword()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void setUserActive(Boolean active, String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE users SET status = ? where email = ?");
            preparedStatement.setBoolean(1, active);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
