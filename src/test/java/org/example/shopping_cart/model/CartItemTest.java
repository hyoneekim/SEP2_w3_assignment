package org.example.shopping_cart.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    @Test
    void constructor_shouldStoreAllFields() {
        CartItem item = new CartItem(1, 10, 2, 9.99, 3, 29.97);

        assertEquals(1,     item.getId());
        assertEquals(10,    item.getCartRecordId());
        assertEquals(2,     item.getItemNumber());
        assertEquals(9.99,  item.getPrice());
        assertEquals(3,     item.getQuantity());
        assertEquals(29.97, item.getSubtotal());
    }

    @Test
    void toString_shouldContainKeyFields() {
        CartItem item = new CartItem(1, 10, 2, 9.99, 3, 29.97);
        String result = item.toString();

        assertTrue(result.contains("9.99"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("29.97"));
    }
}