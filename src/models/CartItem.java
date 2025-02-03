package models;
public class CartItem {

    private int orderId;
    private int customerId;
    private String customer;
    private String product;
    private int size;
    private String colour;
    private String status;
    private int itemId;
    private int quantity;

    // Konstruktor
    public CartItem(int orderId, int customerId, String customer, String product, int size, String colour, String status, int itemId, int quantity) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customer = customer;
        this.product = product;
        this.size = size;
        this.colour = colour;
        this.status = status;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    // Getters & Setters
    public int getOrderId() { return orderId; }
    public int getCustomerId() { return customerId; }
    public String getCustomer() { return customer; }
    public String getProduct() { return product; }
    public int getSize() { return size; }
    public String getColour() { return colour; }
    public String getStatus() { return status; }
    public int getItemId() { return itemId; }
    public int getQuantity() { return quantity; }
}
