package raf.vp.jmijatovic11421rn.repositories;

import java.sql.*;
import java.util.Optional;

abstract public class MySQLAbstractRepository {

    public MySQLAbstractRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected Connection newConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + this.getHost() + ":" + this.getPort() + "/" + this.getDatabaseName(), this.getUsername(), this.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getHost() {
        return "localhost";
    }

    protected int getPort() {
        return 3306;
    }

    protected String getDatabaseName() {
        return "cms_schema";
    }

    protected String getUsername() {
        return "admin";
    }

    protected String getPassword() {
        return "root";
    }

    protected void closeStatement(Statement statement) {
        try {
            Optional.of(statement).get().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void closeResultSet(ResultSet resultSet) {
        try {
            Optional.of(resultSet).get().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void closeConnection(Connection connection) {
        try {
            Optional.of(connection).get().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}