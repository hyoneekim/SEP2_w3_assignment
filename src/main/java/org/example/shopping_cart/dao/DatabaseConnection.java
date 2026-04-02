package org.example.shopping_cart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // 환경변수 우선, 없으면 로컬 개발용 기본값 사용
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
            System.err.println("Failed to close connection: " + e.getMessage());
        }
    }
}