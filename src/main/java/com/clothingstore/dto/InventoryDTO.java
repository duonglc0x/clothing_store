package com.clothingstore.dto;

/**
 * InventoryDTO – Data Transfer Object cho Tồn kho.
 *
 * Chứa thông tin tồn kho cần hiển thị trên View:
 * sản phẩm nào, size/color nào, số lượng bao nhiêu.
 * Bao gồm productName (tên sản phẩm) để hiển thị trực tiếp
 * mà không cần truy vấn thêm entity Product.
 */
public class InventoryDTO {

    private Integer id;           // ID bản ghi tồn kho
    private Integer productId;    // ID sản phẩm liên kết
    private String productName;   // Tên sản phẩm – flatten từ entity Product
    private String size;          // Kích cỡ (VD: "M", "L")
    private String color;         // Màu sắc (VD: "Trắng", "Đen")
    private int quantity;         // Số lượng tồn kho hiện tại

    /** Constructor mặc định */
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

    /** Trả về "Tên SP - Size/Color" – VD: "Áo sơ mi - M/Trắng" */
    @Override
    public String toString() { return productName + " - " + size + "/" + color; }
}
