package com.clothingstore.entity;

import jakarta.persistence.*;

/**
 * Inventory – Entity đại diện cho bảng "inventory" trong cơ sở dữ liệu.
 *
 * 
 *
 * @author clothing-store
 * @version 1.0
 * @see Product
 */
@Entity
@Table(name = "inventory",
       // Ràng buộc duy nhất trên tổ hợp 3 cột: product_id + size + color
       uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "size", "color"}))
public class Inventory {

    /** Khóa chính – ID tự động tăng */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Kích cỡ sản phẩm (VD: "XS", "S", "M", "L", "XL", "XXL", "Free Size").
     */
    @Column(name = "size", nullable = false, length = 10)
    private String size;

    /**
     * Màu sắc sản phẩm (VD: "Trắng", "Đen", "Xanh navy", "Đỏ đô").
     */
    @Column(name = "color", nullable = false, length = 50)
    private String color;

    /**
     * Số lượng tồn kho hiện tại cho biến thể (size + color) này.
     * <p>Bắt buộc, mặc định là 0. Giá trị này được cập nhật khi nhập/xuất kho.</p>
     */
    @Column(name = "quantity", nullable = false)
    private int quantity = 0;

    /**
     * Sản phẩm mà bản ghi tồn kho này thuộc về (quan hệ Nhiều-Một).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // ══════════════════════════════════════════════════════════════════════════
    // CONSTRUCTORS
    // ══════════════════════════════════════════════════════════════════════════

    /** Constructor mặc định – bắt buộc cho Hibernate */
    public Inventory() {}

    /**
     * Constructor đầy đủ tham số để tạo bản ghi tồn kho mới.
     *
     * @param product  sản phẩm liên kết
     * @param size     kích cỡ (VD: "M")
     * @param color    màu sắc (VD: "Trắng")
     * @param quantity số lượng tồn kho
     */
    public Inventory(Product product, String size, String color, int quantity) {
        this.product = product;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }

    // ══════════════════════════════════════════════════════════════════════════
    // GETTERS & SETTERS
    // ══════════════════════════════════════════════════════════════════════════

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    /** @return số lượng tồn kho hiện tại */
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    /** @return sản phẩm liên kết */
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    /**
     * Biểu diễn chuỗi: "Size - Màu (SL: Số lượng)"
     */
    @Override
    public String toString() {
        return size + " - " + color + " (SL: " + quantity + ")";
    }
}
