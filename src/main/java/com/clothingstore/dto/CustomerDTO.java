package com.clothingstore.dto;

/**
 * DTO: Khách hàng
 */
public class CustomerDTO {

    private Integer id;
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private int orderCount;

    public CustomerDTO() {}

    public CustomerDTO(Integer id, String fullName, String phone, String email, String address) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getOrderCount() { return orderCount; }
    public void setOrderCount(int orderCount) { this.orderCount = orderCount; }

    @Override
    public String toString() { return fullName + " - " + phone; }
}
