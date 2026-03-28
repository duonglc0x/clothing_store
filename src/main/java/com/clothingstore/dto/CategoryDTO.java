package com.clothingstore.dto;

/**
 * DTO: Danh mục sản phẩm - dùng cho truyền dữ liệu giữa các tầng
 */
public class CategoryDTO {

    private Integer id;
    private String name;
    private String description;
    private int productCount;

    public CategoryDTO() {}

    public CategoryDTO(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getProductCount() { return productCount; }
    public void setProductCount(int productCount) { this.productCount = productCount; }

    @Override
    public String toString() { return name; }
}
