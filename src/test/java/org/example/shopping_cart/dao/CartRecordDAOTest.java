package org.example.shopping_cart.dao;

import org.example.shopping_cart.model.CartRecord;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartRecordDAOTest {

    @Test
    void insertCartRecord_shouldReturnGeneratedId() throws SQLException {
        Connection conn     = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet keys      = mock(ResultSet.class);

        when(conn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(stmt);
        when(stmt.getGeneratedKeys()).thenReturn(keys);
        when(keys.next()).thenReturn(true);
        when(keys.getInt(1)).thenReturn(42);

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(conn);

            int id = CartRecordDAO.insertCartRecord(3, 29.97, "EN");
            assertEquals(42, id);
        }
    }

    @Test
    void insertCartRecord_shouldReturnMinusOneOnSQLException() throws SQLException {
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(anyString(), anyInt())).thenThrow(new SQLException("DB error"));

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(conn);

            int id = CartRecordDAO.insertCartRecord(3, 29.97, "EN");
            assertEquals(-1, id);
        }
    }

    @Test
    void getCartRecordById_shouldReturnCartRecord() throws SQLException {
        Connection conn     = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs        = mock(ResultSet.class);
        Timestamp now       = new Timestamp(System.currentTimeMillis());

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getInt("total_items")).thenReturn(3);
        when(rs.getDouble("total_cost")).thenReturn(29.97);
        when(rs.getString("language")).thenReturn("EN");
        when(rs.getTimestamp("created_at")).thenReturn(now);

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(conn);

            CartRecord result = CartRecordDAO.getCartRecordById(1);
            assertNotNull(result);
            assertEquals("EN", result.getLanguage());
            assertEquals(29.97, result.getTotalCost());
        }
    }

    @Test
    void getCartRecordById_shouldReturnNullWhenNotFound() throws SQLException {
        Connection conn     = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs        = mock(ResultSet.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(conn);

            assertNull(CartRecordDAO.getCartRecordById(999));
        }
    }

    @Test
    void deleteCartRecord_shouldExecuteWithoutException() throws SQLException {
        Connection conn     = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(conn);

            assertDoesNotThrow(() -> CartRecordDAO.deleteCartRecord(1));
        }
    }
}