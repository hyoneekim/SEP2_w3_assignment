package org.example.shopping_cart.dao;

import org.example.shopping_cart.model.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class CartItemDAO {
    private static final Logger logger = Logger.getLogger(CartItemDAO.class.getName());

    private CartItemDAO() {}
    /**
     * Insert a single cart item linked to a cart record.
     */
    public static void insertCartItem(int cartRecordId, int itemNumber, double price, int quantity) {
        String sql = "INSERT INTO cart_items (cart_record_id, item_number, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            double subtotal = price * quantity;
            stmt.setInt(1, cartRecordId);
            stmt.setInt(2, itemNumber);
            stmt.setDouble(3, price);
            stmt.setInt(4, quantity);
            stmt.setDouble(5, subtotal);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.info("Failed to insert cart item: " + e.getMessage());
        }
    }

    /**
     * Insert all items for a cart record in one call.
     */
    public static void insertAllCartItems(int cartRecordId, List<double[]> items) {
        for (int i = 0; i < items.size(); i++) {
            double price    = items.get(i)[0];
            int    quantity = (int) items.get(i)[1];
            insertCartItem(cartRecordId, i + 1, price, quantity);
        }
    }

    /**
     * Fetch all items belonging to a cart record.
     */
    public static List<CartItem> getItemsByCartRecordId(int cartRecordId) {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT id,cart_record_id,item_number,price, quantity, subtotal FROM cart_items WHERE cart_record_id = ? ORDER BY item_number";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartRecordId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new CartItem(
                        rs.getInt("id"),
                        rs.getInt("cart_record_id"),
                        rs.getInt("item_number"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getDouble("subtotal")
                ));
            }
        } catch (SQLException e) {
            logger.info("Failed to fetch cart items: " + e.getMessage());
        }
        return list;
    }
}