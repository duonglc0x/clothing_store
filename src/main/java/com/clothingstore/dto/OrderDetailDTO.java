package com.clothingstore.dto;

import java.math.BigDecimal;

/**
 * OrderDetailDTO – Data Transfer Object cho Chi tiết đơn hàng.
 *
 * Chứa thông tin một dòng sản phẩm trong đơn hàng:
 * sản phẩm nào, size/color, số lượng, đơn giá, thành tiền.
 * Bao gồm productName (flatten từ Product entity) để hiển thị trực tiếp.
 */
public class OrderDetailDTO {

    private Integer id;           // ID chi tiết đơn hàng
    private Integer productId;    // ID sản phẩm
    private String productName;   // Tên sản phẩm – flatten từ Product
    private String size;          // Kích cỡ
    private String color;         // Màu sắc
    private Integer quantity;     // Số lượng mua
    private BigDecimal unitPrice; // Đơn giá tại thời điểm mua
    private BigDecimal subtotal;  // Thành tiền = unitPrice × quantity

    /** Constructor mặc định */
    public OrderDetailDTO() {}

    /**
     * Constructor có tham số. Tự động tính subtotal = unitPrice × quantity.
     * @param productId   ID sản phẩm
     * @param productName tên sản phẩm
     * @param size        kích cỡ
     * @param color       màu sắc
     * @param quantity    số lượng
     * @param unitPrice   đơn giá
     */
    public OrderDetailDTO(Integer productId, String productName, String size, String color,
                          int quantity, BigDecimal unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        // Tự động tính thành tiền khi khởi tạo
        this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    // ── Getters & Setters ──
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    /** @return thành tiền = đơn giá × số lượng */
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
