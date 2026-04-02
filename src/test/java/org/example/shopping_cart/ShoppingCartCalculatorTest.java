package org.example.shopping_cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ShoppingCartCalculator Tests")
class ShoppingCartCalculatorTest {

    // ── itemTotal ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Normal price x quantity returns correct result")
    void itemTotal_normalValues_returnsCorrectResult() {
        assertEquals(15.0, ShoppingCartCalculator.itemTotal(5.0, 3), 0.001);
    }

    @Test
    @DisplayName("Zero quantity returns zero")
    void itemTotal_zeroQuantity_returnsZero() {
        assertEquals(0.0, ShoppingCartCalculator.itemTotal(9.99, 0), 0.001);
    }

    @Test
    @DisplayName("Zero price returns zero")
    void itemTotal_zeroPrice_returnsZero() {
        assertEquals(0.0, ShoppingCartCalculator.itemTotal(0.0, 5), 0.001);
    }

    @Test
    @DisplayName("Decimal price returns correct result")
    void itemTotal_decimalPrice_returnsCorrectResult() {
        assertEquals(7.47, ShoppingCartCalculator.itemTotal(2.49, 3), 0.001);
    }

    @Test
    @DisplayName("Negative price throws exception")
    void itemTotal_negativePrice_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> ShoppingCartCalculator.itemTotal(-1.0, 2));
    }

    @Test
    @DisplayName("Negative quantity throws exception")
    void itemTotal_negativeQuantity_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> ShoppingCartCalculator.itemTotal(5.0, -1));
    }

    @Test
    @DisplayName("Large values return correct result")
    void itemTotal_largeValues_returnsCorrectResult() {
        assertEquals(999999.99, ShoppingCartCalculator.itemTotal(99999.999, 10), 0.01);
    }

    // ── cartTotal ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Multiple items are summed correctly")
    void cartTotal_multipleItems_returnsSumCorrectly() {
        double[] prices     = {10.0, 5.0, 2.5};
        int[]    quantities = {2,    3,   4};
        // 20 + 15 + 10 = 45
        assertEquals(45.0, ShoppingCartCalculator.cartTotal(prices, quantities), 0.001);
    }

    @Test
    @DisplayName("Single item returns its total")
    void cartTotal_singleItem_returnsSingleItemTotal() {
        assertEquals(12.0,
                ShoppingCartCalculator.cartTotal(new double[]{4.0}, new int[]{3}), 0.001);
    }

    @Test
    @DisplayName("All zero quantities returns zero")
    void cartTotal_allZeroQuantities_returnsZero() {
        double[] prices     = {5.0, 8.0};
        int[]    quantities = {0,   0};
        assertEquals(0.0, ShoppingCartCalculator.cartTotal(prices, quantities), 0.001);
    }

    @Test
    @DisplayName("All zero prices returns zero")
    void cartTotal_allZeroPrices_returnsZero() {
        double[] prices     = {0.0, 0.0};
        int[]    quantities = {3,   5};
        assertEquals(0.0, ShoppingCartCalculator.cartTotal(prices, quantities), 0.001);
    }

    @Test
    @DisplayName("Mismatched arrays throw exception")
    void cartTotal_mismatchedArrays_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> ShoppingCartCalculator.cartTotal(new double[]{1.0, 2.0}, new int[]{1}));
    }

    @Test
    @DisplayName("Empty arrays return zero")
    void cartTotal_emptyArrays_returnsZero() {
        assertEquals(0.0,
                ShoppingCartCalculator.cartTotal(new double[]{}, new int[]{}), 0.001);
    }

    @Test
    @DisplayName("Decimal prices across multiple items are summed accurately")
    void cartTotal_decimalPrices_returnsCorrectSum() {
        double[] prices     = {1.99, 3.49, 2.99};
        int[]    quantities = {2,    1,    3};
        // 3.98 + 3.49 + 8.97 = 16.44
        assertEquals(16.44, ShoppingCartCalculator.cartTotal(prices, quantities), 0.01);
    }
}