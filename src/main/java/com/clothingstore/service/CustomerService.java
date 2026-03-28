package com.clothingstore.service;

import com.clothingstore.dao.CustomerDAO;
import com.clothingstore.entity.Customer;

import java.util.List;

public class CustomerService {

    private final CustomerDAO customerDAO = new CustomerDAO();

    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    public Customer getCustomerById(int id) {
        return customerDAO.findById(id);
    }

    public void addCustomer(Customer customer) {
        customerDAO.save(customer);
    }

    public void updateCustomer(Customer customer) {
        customerDAO.update(customer);
    }

    public void deleteCustomer(int id) {
        customerDAO.deleteById(id);
    }

    public List<Customer> searchCustomers(String keyword) {
        return customerDAO.searchByName(keyword);
    }

    public long countCustomers() {
        return customerDAO.count();
    }
}
