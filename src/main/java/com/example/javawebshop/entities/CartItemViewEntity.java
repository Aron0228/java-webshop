package com.example.javawebshop.entities;

public class CartItemViewEntity {
    private String b64Image;
    private String productName;
    private int size;
    private int quantity;
    private int price;
    private Long productId;

    public CartItemViewEntity(String b64Image, String productName, int size, int quantity, int price, Long productId) {
        this.b64Image = b64Image;
        this.productName = productName;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
    }

    public String getB64Image() {
        return b64Image;
    }
    public void setB64Image(String b64Image) {
        this.b64Image = b64Image;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
