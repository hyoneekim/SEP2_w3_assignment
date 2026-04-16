package org.example.shopping_cart.dao;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocalizationStringDAOTest {

    @Test
    void insertString_shouldExecuteWithoutException() throws SQLException {
        Connection conn     = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(conn);

            assertDoesNotThrow(() -> LocalizationStringDAO.insertString("key1", "value1", "EN"));
        }
    }

    @Test
    void getStringsByLanguage_shouldReturnMap() throws SQLException {
        Connection conn     = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs        = mock(ResultSet.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getString("key")).thenReturn("app.title");
        when(rs.getString("value")).thenReturn("Shopping Cart");

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(conn);

            Map<String, String> result = LocalizationStringDAO.getStringsByLanguage("EN");
            assertEquals(1, result.size());
            assertEquals("Shopping Cart", result.get("app.title"));
        }
    }

    @Test
    void getStringsByLanguage_shouldReturnEmptyMapOnSQLException() throws SQLException {
        Connection conn = mock(Connection.class);
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(conn);

            Map<String, String> result = LocalizationStringDAO.getStringsByLanguage("EN");
            assertTrue(result.isEmpty());
        }
    }

    @Test
    void updateString_shouldExecuteWithoutException() throws SQLException {
        Connection conn     = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(conn);

            assertDoesNotThrow(() -> LocalizationStringDAO.updateString("key1", "newValue", "EN"));
        }
    }

    @Test
    void deleteByLanguage_shouldExecuteWithoutException() throws SQLException {
        Connection conn     = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(conn);

            assertDoesNotThrow(() -> LocalizationStringDAO.deleteByLanguage("EN"));
        }
    }
}
