package com.clothingstore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Category – Entity đại diện cho bảng "categories" trong cơ sở dữ liệu.
 *
 * <p>Lớp này ánh xạ (mapping) đến bảng {@code categories} trong MySQL thông qua
 * JPA/Hibernate annotations. Mỗi đối tượng Category tương ứng với MỘT dòng (row)
 * trong bảng categories.</p>
 *
 * <p><b>Vai trò trong hệ thống:</b> Danh mục sản phẩm (ví dụ: Áo, Quần, Váy, Phụ kiện...)
 * dùng để phân loại các sản phẩm quần áo trong cửa hàng.</p>
 *
 * <p><b>Mối quan hệ:</b></p>
 * <ul>
 *   <li>1 Category → N Product (Một-Nhiều): Một danh mục chứa nhiều sản phẩm</li>
 * </ul>
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
     *
     * <p>{@code @Id} – đánh dấu đây là khóa chính.<br>
     * {@code @GeneratedValue(strategy = GenerationType.IDENTITY)} – MySQL tự động tạo giá trị
     * ID mới (AUTO_INCREMENT) khi insert dòng mới.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Tên danh mục (VD: "Áo sơ mi", "Quần jean", "Váy đầm").
     *
     * <p>{@code nullable = false} – cột này KHÔNG được NULL trong CSDL.<br>
     * {@code length = 100} – độ dài tối đa 100 ký tự VARCHAR(100).</p>
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Mô tả chi tiết về danh mục (tùy chọn).
     *
     * <p>{@code columnDefinition = "TEXT"} – sử dụng kiểu TEXT trong MySQL
     * để lưu trữ mô tả dài không giới hạn độ dài.</p>
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
     * <p>{@code mappedBy = "category"} – trường "category" trong entity Product
     * là phía sở hữu (owning side) của quan hệ này.<br>
     * {@code FetchType.LAZY} – danh sách sản phẩm CHỈ được tải khi truy cập trực tiếp
     * (không tải sẵn cùng Category → tối ưu hiệu suất).</p>
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
