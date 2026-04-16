package org.example.shopping_cart.model;

import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import static org.junit.jupiter.api.Assertions.*;

class CartRecordTest {

    private final Timestamp now = new Timestamp(System.currentTimeMillis());

    @Test
    void constructor_shouldStoreAllFields() {
        CartRecord cr = new CartRecord(1, 5, 49.95, "EN", now);

        assertEquals(1,     cr.getId());
        assertEquals(5,     cr.getTotalItems());
        assertEquals(49.95, cr.getTotalCost());
        assertEquals("EN",  cr.getLanguage());
        assertEquals(now,   cr.getCreatedAt());
    }

    @Test
    void toString_shouldContainKeyFields() {
        CartRecord cr = new CartRecord(1, 5, 49.95, "EN", now);
        String result = cr.toString();

        assertTrue(result.contains("49.95"));
        assertTrue(result.contains("EN"));
        assertTrue(result.contains("5"));
    }
}