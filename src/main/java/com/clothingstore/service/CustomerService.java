package com.clothingstore.service;

import com.clothingstore.dao.CustomerDAO;
import com.clothingstore.entity.Customer;

import java.util.List;

/**
 * CustomerService – Tầng Service cho Khách hàng.
 *
 * Bổ sung thêm phương thức searchCustomers() so với CRUD cơ bản.
 */
public class CustomerService {

    private final CustomerDAO customerDAO = new CustomerDAO();

    /** @return danh sách tất cả khách hàng */
    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    /** @return khách hàng theo ID */
    public Customer getCustomerById(int id) {
        return customerDAO.findById(id);
    }

    /** Thêm khách hàng mới */
    public void addCustomer(Customer customer) {
        customerDAO.save(customer);
    }

    /** Cập nhật thông tin khách hàng */
    public void updateCustomer(Customer customer) {
        customerDAO.update(customer);
    }

    /** Xóa khách hàng theo ID */
    public void deleteCustomer(int id) {
        customerDAO.deleteById(id);
    }

    /**
     * Tìm kiếm khách hàng theo tên (LIKE).
     * @param keyword từ khóa tìm kiếm
     * @return danh sách khách hàng có tên chứa keyword
     */
    public List<Customer> searchCustomers(String keyword) {
        return customerDAO.searchByName(keyword);
    }

    /** @return tổng số khách hàng (dùng cho Dashboard) */
    public long countCustomers() {
        return customerDAO.count();
    }
}
