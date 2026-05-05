package com.clothingstore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Supplier – Entity đại diện cho bảng "suppliers" trong cơ sở dữ liệu.
 *
 * Lưu trữ thông tin các nhà cung cấp hàng hóa (quần áo, phụ kiện) cho cửa hàng.
 * Mỗi nhà cung cấp có thể cung cấp nhiều sản phẩm khác nhau.

 *
 * @author clothing-store
 * @version 1.0
 * @see Product
 */
@Entity
@Table(name = "suppliers")
public class Supplier {

    /** Khóa chính – ID tự động tăng */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Tên nhà cung cấp (tên công ty hoặc cá nhân).
     * Bắt buộc nhập, tối đa 150 ký tự.
     */
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    /** Số điện thoại liên hệ của nhà cung cấp */
    @Column(name = "phone", length = 20)
    private String phone;

    /** Địa chỉ email của nhà cung cấp */
    @Column(name = "email", length = 100)
    private String email;

    /**
     * Địa chỉ trụ sở/kho hàng của nhà cung cấp.
     */
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    /**
     * Tên người liên hệ trực tiếp tại nhà cung cấp.
     */
    @Column(name = "contact_name", length = 100)
    private String contactName;

    /** Thời điểm thêm nhà cung cấp vào hệ thống */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Danh sách sản phẩm do nhà cung cấp này cung cấp (quan hệ Một-Nhiều).
     */
    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    // ══════════════════════════════════════════════════════════════════════════
    // CONSTRUCTORS
    // ══════════════════════════════════════════════════════════════════════════

    /** Constructor mặc định – bắt buộc cho Hibernate */
    public Supplier() {}

    /**
     * Constructor có tham số cơ bản.
     *
     * @param name  tên nhà cung cấp
     * @param phone số điện thoại
     * @param email email liên hệ
     */
    public Supplier(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // ══════════════════════════════════════════════════════════════════════════
    // GETTERS & SETTERS
    // ══════════════════════════════════════════════════════════════════════════

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    /** @return tên người đại diện liên hệ */
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    /**
     * Trả về tên nhà cung cấp – dùng hiển thị trong JComboBox.
     */
    @Override
    public String toString() { return name; }
}
