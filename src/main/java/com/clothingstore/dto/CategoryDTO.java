package com.clothingstore.dto;

/**
 * CategoryDTO – Data Transfer Object cho Danh mục sản phẩm.
 *
 * DTO (Data Transfer Object) là đối tượng chuyên dùng để truyền dữ liệu giữa các tầng
 * trong kiến trúc MVC (View ↔ Controller ↔ Service), KHÔNG chứa logic nghiệp vụ.
 *
 * Khác với Entity (ánh xạ trực tiếp đến bảng CSDL), DTO chỉ chứa những trường
 * cần thiết cho giao diện, tránh lộ cấu trúc CSDL và vấn đề lazy loading.
 */
public class CategoryDTO {

    /** ID danh mục (khóa chính) */
    private Integer id;

    /** Tên danh mục (VD: "Áo", "Quần", "Váy") */
    private String name;

    /** Mô tả danh mục */
    private String description;

    /** Số lượng sản phẩm thuộc danh mục – trường tính toán, không lưu trong Entity */
    private int productCount;

    /** Constructor mặc định */
    public CategoryDTO() {}

    /**
     * Constructor có tham số.
     * @param id          ID danh mục
     * @param name        tên danh mục
     * @param description mô tả
     */
    public CategoryDTO(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // ── Getters & Setters ──
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    /** @return số lượng sản phẩm thuộc danh mục */
    public int getProductCount() { return productCount; }
    public void setProductCount(int productCount) { this.productCount = productCount; }

    /** Trả về tên danh mục – dùng trong JComboBox */
    @Override
    public String toString() { return name; }
}
