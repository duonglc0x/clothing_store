package com.clothingstore.dao;

import com.clothingstore.entity.Order;
import com.clothingstore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

/**
 * OrderDAO – DAO cho entity Order (Đơn hàng).
 *
 * Kế thừa CRUD từ GenericDAO, bổ sung:
 * - findAllWithDetails(): tải đơn hàng kèm thông tin Customer và Employee
 */
public class OrderDAO extends GenericDAO<Order> {

    /** Constructor – truyền Order.class cho GenericDAO */
    public OrderDAO() {
        super(Order.class);
    }

    /**
     * Lấy tất cả đơn hàng kèm thông tin khách hàng và nhân viên.
     *
     * Sử dụng LEFT JOIN FETCH để tải eager các quan hệ Lazy:
     * - LEFT JOIN FETCH o.customer – tải thông tin khách hàng
     * - LEFT JOIN FETCH o.employee – tải thông tin nhân viên xử lý
     *
     * LEFT JOIN (thay vì INNER JOIN) để bao gồm cả các đơn hàng
     * chưa có khách hàng hoặc chưa gán nhân viên.
     *
     * Sắp xếp theo ngày đặt hàng giảm dần (đơn mới nhất lên trước).
     *
     * @return danh sách đơn hàng đã tải đầy đủ thông tin liên quan
     */
    public List<Order> findAllWithDetails() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "SELECT DISTINCT o FROM Order o " +
                "LEFT JOIN FETCH o.customer " +
                "LEFT JOIN FETCH o.employee " +
                "ORDER BY o.orderDate DESC", Order.class
            ).list();
        }
    }
}
