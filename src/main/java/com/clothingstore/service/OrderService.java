package com.clothingstore.service;

import com.clothingstore.dao.OrderDAO;
import com.clothingstore.dao.OrderDetailDAO;
import com.clothingstore.entity.Order;
import com.clothingstore.entity.OrderDetail;

import java.util.List;

public class OrderService {

    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    public List<Order> getAllOrdersWithDetails() {
        return orderDAO.findAllWithDetails();
    }

    public Order getOrderById(int id) {
        return orderDAO.findById(id);
    }

    public void addOrder(Order order) {
        orderDAO.save(order);
    }

    public void updateOrder(Order order) {
        orderDAO.update(order);
    }

    public void deleteOrder(int id) {
        orderDAO.deleteById(id);
    }

    public long countOrders() {
        return orderDAO.count();
    }

    public List<OrderDetail> getOrderDetails(int orderId) {
        return orderDetailDAO.findByOrderId(orderId);
    }
}
