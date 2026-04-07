package com.clothingstore.dto;

/**
 * SupplierDTO – Data Transfer Object cho Nhà cung cấp.
 *
 * Chứa thông tin nhà cung cấp cần hiển thị trên View.
 */
public class SupplierDTO {

    private Integer id;          // ID nhà cung cấp
    private String name;         // Tên nhà cung cấp
    private String phone;        // Số điện thoại
    private String email;        // Email
    private String address;      // Địa chỉ trụ sở
    private String contactName;  // Tên người liên hệ

    /** Constructor mặc định */
    public SupplierDTO() {}

    /**
     * Constructor có tham số.
     * @param id    ID nhà cung cấp
     * @param name  tên
     * @param phone SĐT
     * @param email email
     */
    public SupplierDTO(Integer id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // ── Getters & Setters ──
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

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    /** Trả về tên nhà cung cấp */
    @Override
    public String toString() { return name; }
}
