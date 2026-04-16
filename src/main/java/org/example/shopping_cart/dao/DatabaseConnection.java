package org.example.shopping_cart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Logger;


public class DatabaseConnection {

    private static Connection connection;
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {

            String host     = System.getenv().getOrDefault("DB_HOST",     "localhost");
            String port     = System.getenv().getOrDefault("DB_PORT",     "3306");
            String dbName   = System.getenv().getOrDefault("DB_NAME",     "shopping_cart_localization");
            String user     = System.getenv().getOrDefault("DB_USER",     "root");
            String password = System.getenv().getOrDefault("DB_PASSWORD", "root_password");

            String url = String.format(
                    "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8mb4&serverTimezone=UTC",
                    host, port, dbName
            );

            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.info("Failed to close connection");
        }
    }
}