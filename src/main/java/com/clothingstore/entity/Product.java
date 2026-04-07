package com.clothingstore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Product – Entity đại diện cho bảng "products" trong cơ sở dữ liệu.
 *
 * <p>Đây là entity trung tâm của hệ thống, lưu trữ thông tin sản phẩm quần áo
 * bao gồm: tên, mô tả, giá bán, hình ảnh, và liên kết với danh mục + nhà cung cấp.</p>
 *
 * <p><b>Mối quan hệ:</b></p>
 * <ul>
 *   <li>N Product → 1 Category (Nhiều-Một): Mỗi sản phẩm thuộc một danh mục</li>
 *   <li>N Product → 1 Supplier (Nhiều-Một): Mỗi sản phẩm do một nhà cung cấp cung cấp</li>
 *   <li>1 Product → N Inventory (Một-Nhiều): Mỗi sản phẩm có nhiều bản ghi tồn kho (theo size/color)</li>
 *   <li>1 Product → N OrderDetail (Một-Nhiều): Mỗi sản phẩm có thể xuất hiện trong nhiều chi tiết đơn hàng</li>
 * </ul>
 *
 * @author clothing-store
 * @version 1.0
 * @see Category
 * @see Supplier
 * @see Inventory
 * @see OrderDetail
 */
@Entity
@Table(name = "products")
public class Product {

    /** Khóa chính – ID tự động tăng */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Tên sản phẩm (VD: "Áo sơ mi trắng tay dài", "Quần jean slim fit").
     * <p>Bắt buộc nhập, tối đa 200 ký tự.</p>
     */
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    /**
     * Mô tả chi tiết sản phẩm (chất liệu, phong cách, hướng dẫn giặt...).
     * <p>Kiểu TEXT – không giới hạn độ dài.</p>
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * Giá bán sản phẩm (VNĐ).
     *
     * <p>Sử dụng {@link BigDecimal} với precision=15, scale=2 để đảm bảo
     * tính chính xác khi xử lý tiền tệ. Ví dụ: 350,000.00</p>
     */
    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    /**
     * URL hình ảnh sản phẩm (tùy chọn).
     * <p>Đường dẫn tương đối hoặc URL tuyệt đối đến ảnh sản phẩm.</p>
     */
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    /** Thời điểm thêm sản phẩm vào hệ thống */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Danh mục mà sản phẩm thuộc về (quan hệ Nhiều-Một).
     *
     * <p>{@code @ManyToOne} – Nhiều sản phẩm cùng thuộc một danh mục.<br>
     * {@code @JoinColumn(name = "category_id")} – cột khóa ngoại trong bảng products
     * tham chiếu đến bảng categories.<br>
     * {@code nullable = false} – mỗi sản phẩm BẮT BUỘC phải thuộc một danh mục.<br>
     * {@code FetchType.LAZY} – chỉ tải thông tin danh mục khi cần truy cập.</p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * Nhà cung cấp sản phẩm (quan hệ Nhiều-Một, tùy chọn).
     *
     * <p>Khác với category, supplier có thể NULL (sản phẩm chưa có nhà cung cấp).</p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    /**
     * Danh sách tồn kho (theo kích cỡ và màu sắc) – quan hệ Một-Nhiều.
     *
     * <p>{@code cascade = CascadeType.ALL} – khi thêm/sửa/xóa sản phẩm,
     * các bản ghi Inventory liên quan cũng bị ảnh hưởng tương ứng.<br>
     * {@code orphanRemoval = true} – khi bản ghi Inventory bị loại khỏi danh sách,
     * nó sẽ tự động bị xóa khỏi CSDL.</p>
     */
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventories = new ArrayList<>();

    /**
     * Danh sách chi tiết đơn hàng chứa sản phẩm này (quan hệ Một-Nhiều).
     * <p>Chỉ đọc (không cascade) – việc quản lý OrderDetail do Order đảm nhiệm.</p>
     */
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    // ══════════════════════════════════════════════════════════════════════════
    // CONSTRUCTORS
    // ══════════════════════════════════════════════════════════════════════════

    /** Constructor mặc định – bắt buộc cho Hibernate */
    public Product() {}

    /**
     * Constructor tạo sản phẩm mới với thông tin tối thiểu.
     *
     * @param name     tên sản phẩm
     * @param price    giá bán
     * @param category danh mục thuộc về
     */
    public Product(String name, BigDecimal price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // ══════════════════════════════════════════════════════════════════════════
    // GETTERS & SETTERS
    // ══════════════════════════════════════════════════════════════════════════

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    /** @return giá bán sản phẩm (BigDecimal) */
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    /** @return danh mục mà sản phẩm thuộc về */
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    /** @return nhà cung cấp (có thể null) */
    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }

    /** @return danh sách tồn kho (theo size/color) */
    public List<Inventory> getInventories() { return inventories; }
    public void setInventories(List<Inventory> inventories) { this.inventories = inventories; }

    /** @return danh sách chi tiết đơn hàng liên quan */
    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetail> orderDetails) { this.orderDetails = orderDetails; }

    /** Trả về tên sản phẩm – dùng hiển thị trong JComboBox, JTable */
    @Override
    public String toString() { return name; }
}
