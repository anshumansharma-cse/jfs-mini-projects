package model;

public class SalesRecord {
    private final String product;
    private final String category;
    private final int quantity;
    private final double price;

    public SalesRecord(String product, String category, int quantity, double price) {
        this.product = product; this.category = category; this.quantity = quantity; this.price = price;
    }
    public String getProduct() { return product; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getTotalRevenue() { return quantity * price; }
}
