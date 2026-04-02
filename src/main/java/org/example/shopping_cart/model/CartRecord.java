package org.example.shopping_cart.model;

import java.sql.Timestamp;

public class CartRecord {
    private final int id;
    private final int totalItems;
    private final double totalCost;
    private final String language;
    private final Timestamp createdAt;

    public CartRecord(int id, int totalItems, double totalCost, String language, Timestamp createdAt) {
        this.id         = id;
        this.totalItems = totalItems;
        this.totalCost  = totalCost;
        this.language   = language;
        this.createdAt  = createdAt;
    }

    public int       getId()         { return id; }
    public int       getTotalItems() { return totalItems; }
    public double    getTotalCost()  { return totalCost; }
    public String    getLanguage()   { return language; }
    public Timestamp getCreatedAt()  { return createdAt; }

    @Override
    public String toString() {
        return String.format("CartRecord{id=%d, items=%d, total=%.2f, lang=%s, at=%s}",
                id, totalItems, totalCost, language, createdAt);
    }
}