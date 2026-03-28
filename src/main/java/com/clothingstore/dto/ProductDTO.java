package com.clothingstore.dto;

import java.math.BigDecimal;

/**
 * DTO: Sản phẩm quần áo
 */
public class ProductDTO {

    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private String categoryName;
    private Integer categoryId;
    private String supplierName;
    private Integer supplierId;
    private int totalStock; // Tổng tồn kho tất cả size/color

    public ProductDTO() {}

    public ProductDTO(Integer id, String name, BigDecimal price, String categoryName, String supplierName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
        this.supplierName = supplierName;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public Integer getSupplierId() { return supplierId; }
    public void setSupplierId(Integer supplierId) { this.supplierId = supplierId; }

    public int getTotalStock() { return totalStock; }
    public void setTotalStock(int totalStock) { this.totalStock = totalStock; }

    @Override
    public String toString() { return name; }
}
