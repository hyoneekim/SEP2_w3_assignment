package org.example.shopping_cart.dao;

import org.example.shopping_cart.model.CartRecord;

import java.sql.*;

public class CartRecordDAO {

    /**
     * Insert a new cart record and return its generated ID.
     */
    public static int insertCartRecord(int totalItems, double totalCost, String language) {
        String sql = "INSERT INTO cart_records (total_items, total_cost, language) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, totalItems);
            stmt.setDouble(2, totalCost);
            stmt.setString(3, language);
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Failed to insert cart record: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Fetch a single cart record by ID.
     */
    public static CartRecord getCartRecordById(int id) {
        String sql = "SELECT * FROM cart_records WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch cart record: " + e.getMessage());
        }
        return null;
    }

    /**
     * Delete a cart record by ID (cascades to cart_items).
     */
    public static void deleteCartRecord(int id) {
        String sql = "DELETE FROM cart_records WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete cart record: " + e.getMessage());
        }
    }

    private static CartRecord mapRow(ResultSet rs) throws SQLException {
        return new CartRecord(
                rs.getInt("id"),
                rs.getInt("total_items"),
                rs.getDouble("total_cost"),
                rs.getString("language"),
                rs.getTimestamp("created_at")
        );
    }
}