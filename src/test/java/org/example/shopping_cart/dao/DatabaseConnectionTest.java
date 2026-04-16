package org.example.shopping_cart.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseConnectionTest {

    @AfterEach
    void tearDown() {
        DatabaseConnection.closeConnection();
    }

    @Test
    void getConnection_shouldReturnConnection() throws SQLException {
        Connection mockConn = mock(Connection.class);
        when(mockConn.isClosed()).thenReturn(false);

        try (MockedStatic<DriverManager> mocked = mockStatic(DriverManager.class)) {
            mocked.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConn);

            Connection result = DatabaseConnection.getConnection();
            assertNotNull(result);
        }
    }

    @Test
    void getConnection_shouldReuseExistingConnection() throws SQLException {
        Connection mockConn = mock(Connection.class);
        when(mockConn.isClosed()).thenReturn(false);

        try (MockedStatic<DriverManager> mocked = mockStatic(DriverManager.class)) {
            mocked.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConn);

            Connection first  = DatabaseConnection.getConnection();
            Connection second = DatabaseConnection.getConnection();
            assertSame(first, second);
        }
    }

    @Test
    void getConnection_shouldReconnectIfClosed() throws SQLException {
        Connection closedConn = mock(Connection.class);
        Connection newConn    = mock(Connection.class);
        when(closedConn.isClosed()).thenReturn(true);
        when(newConn.isClosed()).thenReturn(false);

        try (MockedStatic<DriverManager> mocked = mockStatic(DriverManager.class)) {
            mocked.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(closedConn, newConn);

            DatabaseConnection.getConnection();
            Connection result = DatabaseConnection.getConnection();
            assertNotNull(result);
        }
    }

    @Test
    void closeConnection_shouldNotThrow() {
        assertDoesNotThrow(() -> DatabaseConnection.closeConnection());
    }
}
