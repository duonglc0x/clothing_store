package com.clothingstore.dto;

/**
 * DTO: Tồn kho
 */
public class InventoryDTO {

    private Integer id;
    private Integer productId;
    private String productName;
    private String size;
    private String color;
    private int quantity;

    public InventoryDTO() {}

    // ── Getters & Setters ──
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() { return productName + " - " + size + "/" + color; }
}
