package org.example.shopping_cart.dao;

import org.example.shopping_cart.model.CartItem;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartItemDAOTest {

    @Test
    void insertCartItem_shouldExecuteWithoutException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(conn);
            assertDoesNotThrow(() -> CartItemDAO.insertCartItem(1, 1, 9.99, 2));
        }
    }

    @Test
    void insertAllCartItems_shouldCallInsertForEachItem() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(conn);

            List<double[]> items = List.of(
                    new double[]{9.99, 2},
                    new double[]{4.99, 3}
            );
            assertDoesNotThrow(() -> CartItemDAO.insertAllCartItems(1, items));
        }
    }

    @Test
    void getItemsByCartRecordId_shouldReturnList() throws SQLException {
        Connection conn     = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs        = mock(ResultSet.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getInt("cart_record_id")).thenReturn(1);
        when(rs.getInt("item_number")).thenReturn(1);
        when(rs.getDouble("price")).thenReturn(9.99);
        when(rs.getInt("quantity")).thenReturn(2);
        when(rs.getDouble("subtotal")).thenReturn(19.98);

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(conn);

            List<CartItem> result = CartItemDAO.getItemsByCartRecordId(1);
            assertEquals(1, result.size());
            assertEquals(9.99, result.get(0).getPrice());
        }
    }

    @Test
    void getItemsByCartRecordId_shouldReturnEmptyListOnSQLException() throws SQLException {
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(conn);

            List<CartItem> result = CartItemDAO.getItemsByCartRecordId(1);
            assertTrue(result.isEmpty());
        }
    }
}