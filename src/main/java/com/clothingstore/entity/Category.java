package com.clothingstore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Category – Entity đại diện cho bảng "categories" trong cơ sở dữ liệu.
 *
 * @author clothing-store
 * @version 1.0
 * @see Product
 */
@Entity                         // Đánh dấu lớp này là một JPA Entity (ánh xạ đến bảng CSDL)
@Table(name = "categories")     // Chỉ định tên bảng trong MySQL mà Entity này ánh xạ đến
public class Category {

    /**
     * Khóa chính (Primary Key) – ID tự động tăng của danh mục.
     * {@code @GeneratedValue(strategy = GenerationType.IDENTITY)} – MySQL tự động tạo giá trị
     * ID mới (AUTO_INCREMENT) khi insert dòng mới.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Tên danh mục (VD: "Áo sơ mi", "Quần jean", "Váy đầm").
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Mô tả chi tiết về danh mục (tùy chọn).
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * Thời điểm tạo danh mục – được lưu tự động bởi MySQL hoặc application.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Danh sách sản phẩm thuộc danh mục này (quan hệ Một-Nhiều / One-To-Many).
     *
     * {@code mappedBy = "category"} – trường "category" trong entity Product
     * là phía sở hữu (owning side) của quan hệ này.
     * {@code FetchType.LAZY} – danh sách sản phẩm CHỈ được tải khi truy cập trực tiếp
     * (không tải sẵn cùng Category → tối ưu hiệu suất).
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    // ══════════════════════════════════════════════════════════════════════════
    // CONSTRUCTORS – Các hàm khởi tạo
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Constructor mặc định (không tham số).
     * <p>BẮT BUỘC phải có để Hibernate có thể tạo đối tượng thông qua Reflection.</p>
     */
    public Category() {}

    /**
     * Constructor có tham số – tạo danh mục mới với tên và mô tả.
     *
     * @param name        tên danh mục (VD: "Áo khoác")
     * @param description mô tả danh mục (VD: "Các loại áo khoác mùa đông")
     */
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // ══════════════════════════════════════════════════════════════════════════
    // GETTERS & SETTERS – Các phương thức truy cập và thiết lập giá trị
    // ══════════════════════════════════════════════════════════════════════════

    /** @return ID khóa chính của danh mục */
    public Integer getId() { return id; }

    /** @param id giá trị ID cần gán */
    public void setId(Integer id) { this.id = id; }

    /** @return tên danh mục */
    public String getName() { return name; }

    /** @param name tên danh mục mới */
    public void setName(String name) { this.name = name; }

    /** @return mô tả danh mục */
    public String getDescription() { return description; }

    /** @param description mô tả mới */
    public void setDescription(String description) { this.description = description; }

    /** @return thời điểm tạo danh mục */
    public LocalDateTime getCreatedAt() { return createdAt; }

    /** @param createdAt thời điểm tạo mới */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    /** @return danh sách sản phẩm thuộc danh mục này */
    public List<Product> getProducts() { return products; }

    /** @param products danh sách sản phẩm mới */
    public void setProducts(List<Product> products) { this.products = products; }

    /**
     * Biểu diễn chuỗi của danh mục – trả về tên danh mục.
     * <p>Được sử dụng khi hiển thị trong JComboBox, JList hoặc log.</p>
     *
     * @return tên danh mục
     */
    @Override
    public String toString() { return name; }
}
