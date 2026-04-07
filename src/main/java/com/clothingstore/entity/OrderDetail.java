package com.clothingstore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * OrderDetail – Entity bảng "order_details". Lưu chi tiết từng sản phẩm trong đơn hàng.
 *
 * Mỗi OrderDetail = 1 dòng sản phẩm trong đơn, bao gồm: sản phẩm nào,
 * size/color nào, số lượng bao nhiêu, đơn giá bao nhiêu.
 *
 * Mối quan hệ:
 * - N OrderDetail → 1 Order (Nhiều-Một): thuộc về một đơn hàng
 * - N OrderDetail → 1 Product (Nhiều-Một): tham chiếu đến sản phẩm
 */
@Entity
@Table(name = "order_details")
public class OrderDetail {

    /** Khóa chính – ID tự động tăng */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Kích cỡ sản phẩm được mua (VD: "M", "L", "XL") */
    @Column(name = "size", length = 10)
    private String size;

    /** Màu sắc sản phẩm được mua (VD: "Trắng", "Đen") */
    @Column(name = "color", length = 50)
    private String color;

    /** Số lượng mua, mặc định = 1 */
    @Column(name = "quantity", nullable = false)
    private int quantity = 1;

    /**
     * Đơn giá tại thời điểm mua (snapshot giá).
     * Lưu giá tại thời điểm đặt hàng, không bị ảnh hưởng khi giá sản phẩm thay đổi sau này.
     */
    @Column(name = "unit_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPrice;

    /** Đơn hàng chứa chi tiết này (quan hệ Nhiều-Một, bắt buộc) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /** Sản phẩm được mua (quan hệ Nhiều-Một, bắt buộc) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // ── Constructors ──
    /** Constructor mặc định – bắt buộc cho Hibernate */
    public OrderDetail() {}

    /**
     * Constructor đầy đủ tham số.
     * @param order     đơn hàng chứa chi tiết này
     * @param product   sản phẩm được mua
     * @param size      kích cỡ
     * @param color     màu sắc
     * @param quantity  số lượng
     * @param unitPrice đơn giá tại thời điểm mua
     */
    public OrderDetail(Order order, Product product, String size, String color, int quantity, BigDecimal unitPrice) {
        this.order = order;
        this.product = product;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // ── Getters & Setters ──
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    /**
     * Tính thành tiền cho dòng chi tiết này = đơn giá × số lượng.
     * @return thành tiền (BigDecimal)
     */
    public BigDecimal getSubTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    /** Trả về "Tên SP xSố lượng" – VD: "Áo sơ mi x2" */
    @Override
    public String toString() {
        return product.getName() + " x" + quantity;
    }
}
