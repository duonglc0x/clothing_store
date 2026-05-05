package com.clothingstore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Employee – Entity đại diện cho bảng "employees" trong cơ sở dữ liệu.
 *
 *
 * @author clothing-store
 * @version 1.0
 * @see Order
 */
@Entity
@Table(name = "employees")
public class Employee {

    /** Khóa chính – ID tự động tăng */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Họ và tên nhân viên.
     * <p>Bắt buộc nhập, tối đa 150 ký tự.</p>
     */
    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    /** Số điện thoại nhân viên */
    @Column(name = "phone", length = 20)
    private String phone;

    /**
     * Email nhân viên.
     * <p>{@code unique = true} – đảm bảo mỗi nhân viên có email duy nhất.</p>
     */
    @Column(name = "email", length = 100, unique = true)
    private String email;

    /**
     * Chức vụ/vị trí làm việc (VD: "Nhân viên bán hàng", "Quản lý kho", "Trưởng ca").
     */
    @Column(name = "position", length = 100)
    private String position;

    /**
     * Mức lương của nhân viên.
     *
     * {@code precision = 15, scale = 2} – hỗ trợ số có tối đa 15 chữ số,
     * trong đó 2 chữ số thập phân. Ví dụ: 12,000,000.00 VNĐ.<br>
     * Sử dụng {@link BigDecimal} thay vì double/float để đảm bảo độ chính xác
     * khi tính toán tiền tệ (tránh lỗi làm tròn số thực dấu phẩy động).
     */
    @Column(name = "salary", precision = 15, scale = 2)
    private BigDecimal salary = BigDecimal.ZERO;

    /**
     * Ngày bắt đầu làm việc (ngày vào làm).
     * Sử dụng {@link LocalDate} – chỉ lưu ngày, không lưu giờ.
     */
    @Column(name = "hire_date")
    private LocalDate hireDate;

    /** Thời điểm tạo bản ghi nhân viên */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Danh sách đơn hàng do nhân viên này xử lý (quan hệ Một-Nhiều).
     *
     * {@code mappedBy = "employee"} – trường "employee" trong entity Order
     * là phía sở hữu quan hệ.
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    // ══════════════════════════════════════════════════════════════════════════
    // CONSTRUCTORS
    // ══════════════════════════════════════════════════════════════════════════

    /** Constructor mặc định – bắt buộc cho Hibernate */
    public Employee() {}

    /**
     * Constructor có tham số cơ bản.
     *
     * @param fullName họ và tên
     * @param phone    số điện thoại
     * @param email    email
     * @param position chức vụ
     */
    public Employee(String fullName, String phone, String email, String position) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.position = position;
    }

    // ══════════════════════════════════════════════════════════════════════════
    // GETTERS & SETTERS
    // ══════════════════════════════════════════════════════════════════════════

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    /** @return mức lương (BigDecimal để đảm bảo chính xác tiền tệ) */
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    /** @return ngày vào làm */
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }

    /**
     * Trả về họ tên nhân viên – dùng hiển thị trong JComboBox, JTable.
     */
    @Override
    public String toString() { return fullName; }
}
