package com.clothingstore.controller;

import com.clothingstore.entity.Customer;
import com.clothingstore.service.CustomerService;

import java.util.List;

/**
 * CustomerController – Tầng Controller cho Khách hàng.
 *
 * Controller là cầu nối giữa View (giao diện) và Service (nghiệp vụ):
 * - Nhận dữ liệu thô từ View (các tham số String, int...)
 * - Chuyển đổi thành Entity object
 * - Gọi Service để xử lý nghiệp vụ và truy cập CSDL
 * - Trả kết quả về cho View hiển thị
 *
 * Luồng dữ liệu: View → Controller → Service → DAO → Database
 */
public class CustomerController {

    /** Service xử lý nghiệp vụ khách hàng */
    private final CustomerService customerService = new CustomerService();

    /**
     * Lấy danh sách tất cả khách hàng.
     * @return danh sách Customer entity
     */
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    /**
     * Thêm khách hàng mới.
     * Nhận dữ liệu thô từ View, tạo Entity, lưu vào CSDL.
     *
     * @param fullName họ tên
     * @param phone    SĐT
     * @param email    email
     * @param address  địa chỉ
     */
    public void addCustomer(String fullName, String phone, String email, String address) {
        Customer c = new Customer();       // Tạo entity mới
        c.setFullName(fullName);           // Gán dữ liệu từ View
        c.setPhone(phone);
        c.setEmail(email);
        c.setAddress(address);
        customerService.addCustomer(c);    // Gọi Service để lưu vào CSDL
    }

    /**
     * Cập nhật thông tin khách hàng.
     * Tìm entity cũ theo ID → cập nhật các trường → lưu lại.
     *
     * @param id       ID khách hàng cần cập nhật
     * @param fullName họ tên mới
     * @param phone    SĐT mới
     * @param email    email mới
     * @param address  địa chỉ mới
     */
    public void updateCustomer(int id, String fullName, String phone, String email, String address) {
        Customer c = customerService.getCustomerById(id);  // Tìm entity hiện tại
        if (c != null) {                                    // Kiểm tra tồn tại
            c.setFullName(fullName);
            c.setPhone(phone);
            c.setEmail(email);
            c.setAddress(address);
            customerService.updateCustomer(c);              // Cập nhật vào CSDL
        }
    }

    /** Xóa khách hàng theo ID */
    public void deleteCustomer(int id) {
        customerService.deleteCustomer(id);
    }

    /** @return tổng số khách hàng (dùng cho Dashboard) */
    public long countCustomers() {
        return customerService.countCustomers();
    }
}
