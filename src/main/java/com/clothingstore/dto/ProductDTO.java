package com.clothingstore.dto;

import java.math.BigDecimal;

/**
 * ProductDTO – Data Transfer Object cho Sản phẩm quần áo.
 *
 * Chứa thông tin sản phẩm đã được "flatten" (làm phẳng) từ nhiều entity:
 * - Tên, mô tả, giá từ Product
 * - Tên danh mục từ Category (thay vì embed cả entity Category)
 * - Tên nhà cung cấp từ Supplier
 * - Tổng tồn kho (tính từ tất cả bản ghi Inventory)
 */
public class ProductDTO {

    private Integer id;           // ID sản phẩm
    private String name;          // Tên sản phẩm
    private String description;   // Mô tả sản phẩm
    private BigDecimal price;     // Giá bán (BigDecimal cho chính xác tiền tệ)
    private String categoryName;  // Tên danh mục – flatten từ Category entity
    private Integer categoryId;   // ID danh mục – dùng khi cần cập nhật
    private String supplierName;  // Tên nhà cung cấp – flatten từ Supplier entity
    private Integer supplierId;   // ID nhà cung cấp
    private int totalStock;       // Tổng tồn kho tất cả size/color – trường tính toán

    /** Constructor mặc định */
    public ProductDTO() {}

    /**
     * Constructor có tham số.
     * @param id           ID sản phẩm
     * @param name         tên sản phẩm
     * @param price        giá bán
     * @param categoryName tên danh mục
     * @param supplierName tên nhà cung cấp
     */
    public ProductDTO(Integer id, String name, BigDecimal price, String categoryName, String supplierName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
        this.supplierName = supplierName;
    }

    // ── Getters & Setters ──
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

    /** @return tổng tồn kho tất cả biến thể size/color */
    public int getTotalStock() { return totalStock; }
    public void setTotalStock(int totalStock) { this.totalStock = totalStock; }

    /** Trả về tên sản phẩm – dùng trong JComboBox */
    @Override
    public String toString() { return name; }
}
