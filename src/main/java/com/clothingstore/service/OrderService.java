package com.clothingstore.service;

import com.clothingstore.dao.OrderDAO;
import com.clothingstore.dao.OrderDetailDAO;
import com.clothingstore.entity.Order;
import com.clothingstore.entity.OrderDetail;

import java.util.List;

/**
 * OrderService – Tầng Service cho Đơn hàng.
 *
 * Quản lý cả Order và OrderDetail thông qua 2 DAO:
 * - OrderDAO: thao tác với bảng orders
 * - OrderDetailDAO: thao tác với bảng order_details
 */
public class OrderService {

    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

    /** @return tất cả đơn hàng (không kèm thông tin liên quan) */
    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    /**
     * Lấy tất cả đơn hàng KÈM thông tin Customer và Employee.
     * Sử dụng JOIN FETCH để tránh lỗi LazyInitializationException.
     * @return danh sách đơn hàng đầy đủ thông tin
     */
    public List<Order> getAllOrdersWithDetails() {
        return orderDAO.findAllWithDetails();
    }

    /** @return đơn hàng theo ID */
    public Order getOrderById(int id) {
        return orderDAO.findById(id);
    }

    /** Tạo đơn hàng mới */
    public void addOrder(Order order) {
        orderDAO.save(order);
    }

    /** Cập nhật đơn hàng */
    public void updateOrder(Order order) {
        orderDAO.update(order);
    }

    /** Xóa đơn hàng theo ID (cascade xóa cả OrderDetail) */
    public void deleteOrder(int id) {
        orderDAO.deleteById(id);
    }

    /** @return tổng số đơn hàng (dùng cho Dashboard) */
    public long countOrders() {
        return orderDAO.count();
    }

    /**
     * Lấy danh sách chi tiết của một đơn hàng.
     * @param orderId ID đơn hàng
     * @return danh sách OrderDetail kèm thông tin Product
     */
    public List<OrderDetail> getOrderDetails(int orderId) {
        return orderDetailDAO.findByOrderId(orderId);
    }
}
