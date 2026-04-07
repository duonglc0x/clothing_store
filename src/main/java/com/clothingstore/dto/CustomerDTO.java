package com.clothingstore.dto;

/**
 * CustomerDTO – Data Transfer Object cho Khách hàng.
 *
 * Chứa thông tin cần hiển thị trên View: họ tên, SĐT, email, địa chỉ,
 * và số đơn hàng (trường tính toán không có trong Entity).
 */
public class CustomerDTO {

    private Integer id;
    private String fullName;    // Họ tên khách hàng
    private String phone;       // Số điện thoại
    private String email;       // Email
    private String address;     // Địa chỉ
    private int orderCount;     // Số đơn hàng đã đặt – trường tổng hợp cho thống kê

    /** Constructor mặc định */
    public CustomerDTO() {}

    /**
     * Constructor có tham số.
     * @param id       ID khách hàng
     * @param fullName họ tên
     * @param phone    SĐT
     * @param email    email
     * @param address  địa chỉ
     */
    public CustomerDTO(Integer id, String fullName, String phone, String email, String address) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;
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

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getOrderCount() { return orderCount; }
    public void setOrderCount(int orderCount) { this.orderCount = orderCount; }

    /** Trả về "Họ tên - SĐT" – dùng hiển thị trong JComboBox */
    @Override
    public String toString() { return fullName + " - " + phone; }
}
