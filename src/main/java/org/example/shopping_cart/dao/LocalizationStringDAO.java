package org.example.shopping_cart.dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class LocalizationStringDAO {
    private static final Logger logger = Logger.getLogger(LocalizationStringDAO.class.getName());

    private LocalizationStringDAO() {}
    /**
     * Insert a single localization key-value pair.
     */
    public static void insertString(String key, String value, String language) {
        String sql = "INSERT INTO localization_strings (`key`, value, language) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, key);
            stmt.setString(2, value);
            stmt.setString(3, language);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.info("Failed to insert localization string: " + e.getMessage());
        }
    }

    /**
     * Fetch all key-value pairs for a given language as a Map.
     */
    public static Map<String, String> getStringsByLanguage(String language) {
        Map<String, String> map = new HashMap<>();
        String sql = "SELECT `key`, value FROM localization_strings WHERE language = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, language);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("key"), rs.getString("value"));
            }
        } catch (SQLException e) {
            logger.info("Failed to fetch localization strings: " + e.getMessage());
        }
        return map;
    }

    /**
     * Update an existing localization value.
     */
    public static void updateString(String key, String value, String language) {
        String sql = "UPDATE localization_strings SET value = ? WHERE `key` = ? AND language = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, value);
            stmt.setString(2, key);
            stmt.setString(3, language);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.info("Failed to update localization string: " + e.getMessage());
        }
    }

    /**
     * Delete all strings for a language.
     */
    public static void deleteByLanguage(String language) {
        String sql = "DELETE FROM localization_strings WHERE language = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, language);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.info("Failed to delete localization strings: " + e.getMessage());
        }
    }
}