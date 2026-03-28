package com.clothingstore.dao;

import com.clothingstore.entity.OrderDetail;
import com.clothingstore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class OrderDetailDAO extends GenericDAO<OrderDetail> {

    public OrderDetailDAO() {
        super(OrderDetail.class);
    }

    public List<OrderDetail> findByOrderId(int orderId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM OrderDetail od LEFT JOIN FETCH od.product WHERE od.order.id = :oid",
                OrderDetail.class
            ).setParameter("oid", orderId).list();
        }
    }
}
