package com.clothingstore.entity;

import jakarta.persistence.*;

/**
 * Inventory – Entity đại diện cho bảng "inventory" trong cơ sở dữ liệu.
 *
 * <p>Quản lý tồn kho chi tiết cho từng biến thể (variant) của sản phẩm,
 * phân biệt theo <b>kích cỡ (size)</b> và <b>màu sắc (color)</b>.</p>
 *
 * <p><b>Ví dụ:</b> Sản phẩm "Áo sơ mi trắng" có thể có các bản ghi tồn kho:
 * <ul>
 *   <li>Size S, Trắng, Số lượng: 15</li>
 *   <li>Size M, Trắng, Số lượng: 20</li>
 *   <li>Size L, Đen, Số lượng: 10</li>
 * </ul>
 * </p>
 *
 * <p><b>Ràng buộc UNIQUE:</b> Mỗi tổ hợp (product_id, size, color) phải là duy nhất
 * – không thể có 2 bản ghi tồn kho cho cùng sản phẩm + size + color.</p>
 *
 * <p><b>Mối quan hệ:</b></p>
 * <ul>
 *   <li>N Inventory → 1 Product (Nhiều-Một): Nhiều biến thể tồn kho thuộc một sản phẩm</li>
 * </ul>
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
     * <p>Bắt buộc nhập, tối đa 10 ký tự.</p>
     */
    @Column(name = "size", nullable = false, length = 10)
    private String size;

    /**
     * Màu sắc sản phẩm (VD: "Trắng", "Đen", "Xanh navy", "Đỏ đô").
     * <p>Bắt buộc nhập, tối đa 50 ký tự.</p>
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
     *
     * <p>{@code @JoinColumn(name = "product_id")} – cột khóa ngoại tham chiếu
     * đến bảng products. Bắt buộc phải có sản phẩm (nullable = false).</p>
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
     * <p>Ví dụ: "M - Trắng (SL: 15)"</p>
     */
    @Override
    public String toString() {
        return size + " - " + color + " (SL: " + quantity + ")";
    }
}
