package com.clothingstore.controller;

import com.clothingstore.entity.Customer;
import com.clothingstore.entity.Employee;
import com.clothingstore.entity.Order;
import com.clothingstore.entity.OrderDetail;
import com.clothingstore.service.CustomerService;
import com.clothingstore.service.EmployeeService;
import com.clothingstore.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderController {

    private final OrderService orderService = new OrderService();
    private final CustomerService customerService = new CustomerService();
    private final EmployeeService employeeService = new EmployeeService();

    public List<Order> getAllOrders() {
        return orderService.getAllOrdersWithDetails();
    }

    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    public void addOrder(Integer customerId, Integer employeeId, BigDecimal totalAmount,
                         BigDecimal discount, String status, String note) {
        Order order = new Order();
        if (customerId != null) {
            order.setCustomer(customerService.getCustomerById(customerId));
        }
        if (employeeId != null) {
            order.setEmployee(employeeService.getEmployeeById(employeeId));
        }
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount != null ? totalAmount : BigDecimal.ZERO);
        order.setDiscount(discount != null ? discount : BigDecimal.ZERO);
        order.setStatus(Order.Status.valueOf(status));
        order.setNote(note);
        orderService.addOrder(order);
    }

    public void updateOrder(int id, Integer customerId, Integer employeeId,
                            BigDecimal totalAmount, BigDecimal discount, String status, String note) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            if (customerId != null) {
                order.setCustomer(customerService.getCustomerById(customerId));
            }
            if (employeeId != null) {
                order.setEmployee(employeeService.getEmployeeById(employeeId));
            }
            order.setTotalAmount(totalAmount != null ? totalAmount : BigDecimal.ZERO);
            order.setDiscount(discount != null ? discount : BigDecimal.ZERO);
            order.setStatus(Order.Status.valueOf(status));
            order.setNote(note);
            orderService.updateOrder(order);
        }
    }

    public void deleteOrder(int id) {
        orderService.deleteOrder(id);
    }

    public long countOrders() {
        return orderService.countOrders();
    }

    public List<OrderDetail> getOrderDetails(int orderId) {
        return orderService.getOrderDetails(orderId);
    }
}
