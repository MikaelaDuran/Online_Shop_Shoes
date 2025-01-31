package models;

public class Item {
    private int id;
    private int productId;
    private String colour;
    private int size;
    private int stock;

    public Item(int id, int productId, String colour, int size, int stock) {
        this.id = id;
        this.productId = productId;
        this.colour = colour;
        this.size = size;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
