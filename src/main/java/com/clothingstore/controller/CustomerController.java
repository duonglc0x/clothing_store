package com.clothingstore.controller;

import com.clothingstore.entity.Customer;
import com.clothingstore.service.CustomerService;

import java.util.List;

public class CustomerController {

    private final CustomerService customerService = new CustomerService();

    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void addCustomer(String fullName, String phone, String email, String address) {
        Customer c = new Customer();
        c.setFullName(fullName);
        c.setPhone(phone);
        c.setEmail(email);
        c.setAddress(address);
        customerService.addCustomer(c);
    }

    public void updateCustomer(int id, String fullName, String phone, String email, String address) {
        Customer c = customerService.getCustomerById(id);
        if (c != null) {
            c.setFullName(fullName);
            c.setPhone(phone);
            c.setEmail(email);
            c.setAddress(address);
            customerService.updateCustomer(c);
        }
    }

    public void deleteCustomer(int id) {
        customerService.deleteCustomer(id);
    }

    public long countCustomers() {
        return customerService.countCustomers();
    }
}
