package com.clothingstore.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * EmployeeDTO – Data Transfer Object cho Nhân viên.
 *
 * Chứa thông tin nhân viên cần hiển thị trên View:
 * họ tên, SĐT, email, chức vụ, lương, ngày vào làm.
 */
public class EmployeeDTO {

    private Integer id;
    private String fullName;    // Họ tên nhân viên
    private String phone;       // Số điện thoại
    private String email;       // Email
    private String position;    // Chức vụ (VD: "Nhân viên bán hàng")
    private BigDecimal salary;  // Mức lương (BigDecimal cho chính xác tiền tệ)
    private LocalDate hireDate; // Ngày vào làm

    /** Constructor mặc định */
    public EmployeeDTO() {}

    /**
     * Constructor có tham số cơ bản.
     * @param id       ID nhân viên
     * @param fullName họ tên
     * @param phone    SĐT
     * @param email    email
     * @param position chức vụ
     */
    public EmployeeDTO(Integer id, String fullName, String phone, String email, String position) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.position = position;
    }

    // ── Getters & Setters ──
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

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    /** Trả về họ tên nhân viên */
    @Override
    public String toString() { return fullName; }
}
