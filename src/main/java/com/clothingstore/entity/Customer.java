package com.clothingstore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer – Entity đại diện cho bảng "customers" trong cơ sở dữ liệu.

 * @author clothing-store
 * @version 1.0
 * @see Order
 */
@Entity                         // Đánh dấu là JPA Entity
@Table(name = "customers")      // Ánh xạ đến bảng "customers" trong CSDL
public class Customer {

    /**
     * Khóa chính – ID tự động tăng (AUTO_INCREMENT) của khách hàng.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Họ và tên đầy đủ của khách hàng.
     * Bắt buộc nhập (nullable = false), tối đa 150 ký tự.
     */
    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    /**
     * Số điện thoại khách hàng.
     * {@code unique = true} – mỗi số điện thoại phải là duy nhất trong hệ thống,
     * dùng để định danh khách hàng.
     */
    @Column(name = "phone", length = 20, unique = true)
    private String phone;

    /**
     * Địa chỉ email của khách hàng (tùy chọn).
     */
    @Column(name = "email", length = 100)
    private String email;

    /**
     * Địa chỉ giao hàng/liên hệ của khách hàng.
     * Sử dụng kiểu TEXT để hỗ trợ địa chỉ dài.
     */
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    /**
     * Thời điểm tạo bản ghi khách hàng trong hệ thống.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Danh sách đơn hàng của khách hàng này (quan hệ Một-Nhiều).
     *
     * {@code mappedBy = "customer"} – phía sở hữu là trường "customer" trong entity Order.<br>
     * {@code FetchType.LAZY} – chỉ tải danh sách đơn hàng khi cần truy cập.
     */
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    // ══════════════════════════════════════════════════════════════════════════
    // CONSTRUCTORS
    // ══════════════════════════════════════════════════════════════════════════

    /** Constructor mặc định – bắt buộc cho Hibernate. */
    public Customer() {}

    /**
     * Constructor có tham số cơ bản.
     *
     * @param fullName họ và tên khách hàng
     * @param phone    số điện thoại
     * @param email    địa chỉ email
     */
    public Customer(String fullName, String phone, String email) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
    }

    // ══════════════════════════════════════════════════════════════════════════
    // GETTERS & SETTERS
    // ══════════════════════════════════════════════════════════════════════════

    /** @return ID khách hàng */
    public Integer getId() { return id; }
    /** @param id ID cần gán */
    public void setId(Integer id) { this.id = id; }

    /** @return họ tên đầy đủ */
    public String getFullName() { return fullName; }
    /** @param fullName họ tên mới */
    public void setFullName(String fullName) { this.fullName = fullName; }

    /** @return số điện thoại */
    public String getPhone() { return phone; }
    /** @param phone số điện thoại mới */
    public void setPhone(String phone) { this.phone = phone; }

    /** @return email */
    public String getEmail() { return email; }
    /** @param email email mới */
    public void setEmail(String email) { this.email = email; }

    /** @return địa chỉ */
    public String getAddress() { return address; }
    /** @param address địa chỉ mới */
    public void setAddress(String address) { this.address = address; }

    /** @return thời điểm tạo bản ghi */
    public LocalDateTime getCreatedAt() { return createdAt; }
    /** @param createdAt thời điểm tạo mới */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    /** @return danh sách đơn hàng */
    public List<Order> getOrders() { return orders; }
    /** @param orders danh sách đơn hàng mới */
    public void setOrders(List<Order> orders) { this.orders = orders; }

    /**
     * Trả về họ tên khách hàng – dùng để hiển thị trong JComboBox, JTable.
     * @return họ tên đầy đủ
     */
    @Override
    public String toString() { return fullName; }
}
