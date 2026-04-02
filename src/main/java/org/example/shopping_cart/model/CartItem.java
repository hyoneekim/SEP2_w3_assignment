package org.example.shopping_cart.model;

public class CartItem {
    private final int    id;
    private final int    cartRecordId;
    private final int    itemNumber;
    private final double price;
    private final int    quantity;
    private final double subtotal;

    public CartItem(int id, int cartRecordId, int itemNumber, double price, int quantity, double subtotal) {
        this.id           = id;
        this.cartRecordId = cartRecordId;
        this.itemNumber   = itemNumber;
        this.price        = price;
        this.quantity     = quantity;
        this.subtotal     = subtotal;
    }

    public int    getId()           { return id; }
    public int    getCartRecordId() { return cartRecordId; }
    public int    getItemNumber()   { return itemNumber; }
    public double getPrice()        { return price; }
    public int    getQuantity()     { return quantity; }
    public double getSubtotal()     { return subtotal; }

    @Override
    public String toString() {
        return String.format("CartItem{#%d, price=%.2f, qty=%d, subtotal=%.2f}",
                itemNumber, price, quantity, subtotal);
    }
}