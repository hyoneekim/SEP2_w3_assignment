package org.example.shopping_cart;

/**
 * Pure calculation logic — no JavaFX dependency.
 * Keeps business logic testable with JUnit 5 + JaCoCo.
 */
class ShoppingCartCalculator {
    private ShoppingCartCalculator() {}

    /** Returns price × quantity for a single item. */
    public static double itemTotal(double price, int quantity) {
        if (price < 0 || quantity < 0) {
            throw new IllegalArgumentException("Price and quantity must be non-negative.");
        }
        return price * quantity;
    }

    /** Sums all item totals in the cart. */
    public static double cartTotal(double[] prices, int[] quantities) {
        if (prices.length != quantities.length) {
            throw new IllegalArgumentException("Prices and quantities arrays must have the same length.");
        }
        double total = 0;
        for (int i = 0; i < prices.length; i++) {
            total += itemTotal(prices[i], quantities[i]);
        }
        return total;
    }
}
