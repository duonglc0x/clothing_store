package com.clothingstore.dao;

import com.clothingstore.entity.Order;
import com.clothingstore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class OrderDAO extends GenericDAO<Order> {

    public OrderDAO() {
        super(Order.class);
    }

    /**
     * Lấy tất cả đơn hàng kèm thông tin khách hàng và nhân viên.
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
