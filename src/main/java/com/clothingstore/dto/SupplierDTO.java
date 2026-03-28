package com.clothingstore.dto;

/**
 * DTO: Nhà cung cấp
 */
public class SupplierDTO {

    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String contactName;

    public SupplierDTO() {}

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

    @Override
    public String toString() { return name; }
}
