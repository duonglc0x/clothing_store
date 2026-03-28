package com.clothingstore.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO: Nhân viên
 */
public class EmployeeDTO {

    private Integer id;
    private String fullName;
    private String phone;
    private String email;
    private String position;
    private BigDecimal salary;
    private LocalDate hireDate;

    public EmployeeDTO() {}

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

    @Override
    public String toString() { return fullName; }
}
