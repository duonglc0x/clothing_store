package com.clothingstore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Order – Entity bảng "orders". Lưu thông tin đơn hàng bán hàng.
 *
 * Mối quan hệ:
 * - N Order → 1 Customer (Nhiều-Một)
 * - N Order → 1 Employee (Nhiều-Một)
 * - 1 Order → N OrderDetail (Một-Nhiều, cascade ALL + orphanRemoval)
 */
@Entity
@Table(name = "orders")
public class Order {

    /**
     * Enum trạng thái đơn hàng:
     * PENDING(Chờ xử lý), CONFIRMED(Đã xác nhận), SHIPPING(Đang giao),
     * DELIVERED(Đã giao), CANCELLED(Đã hủy).
     */
    public enum Status {
        PENDING, CONFIRMED, SHIPPING, DELIVERED, CANCELLED
    }

    /** Khóa chính – ID tự động tăng */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Ngày giờ đặt hàng – gán LocalDateTime.now() khi tạo đơn mới */
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    /** Tổng tiền đơn hàng (trước giảm giá), BigDecimal đảm bảo chính xác tiền tệ */
    @Column(name = "total_amount", precision = 15, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    /** Số tiền giảm giá. Thành tiền cuối = totalAmount - discount */
    @Column(name = "discount", precision = 15, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    /** Trạng thái đơn – lưu dạng chuỗi (EnumType.STRING), mặc định PENDING */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.PENDING;

    /** Ghi chú đơn hàng (tùy chọn) */
    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    /** Khách hàng đặt đơn (có thể NULL nếu khách vãng lai) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /** Nhân viên xử lý đơn (có thể NULL nếu chưa gán) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    /**
     * Danh sách chi tiết đơn hàng – cascade ALL + orphanRemoval.
     * Khi lưu/xóa Order, các OrderDetail cũng được xử lý tương ứng.
     */
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    // ── Constructors ──
    /** Constructor mặc định – bắt buộc cho Hibernate */
    public Order() {}

    // ── Getters & Setters ──
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetail> orderDetails) { this.orderDetails = orderDetails; }

    /** Trả về "Đơn hàng #ID" – dùng trong log và debug */
    @Override
    public String toString() { return "Đơn hàng #" + id; }
}
