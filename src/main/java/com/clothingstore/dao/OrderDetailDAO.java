package com.clothingstore.dao;

import com.clothingstore.entity.OrderDetail;
import com.clothingstore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

/**
 * OrderDetailDAO – DAO cho entity OrderDetail (Chi tiết đơn hàng).
 *
 * Kế thừa CRUD từ GenericDAO, bổ sung:
 * - findByOrderId(): tìm tất cả chi tiết theo ID đơn hàng
 */
public class OrderDetailDAO extends GenericDAO<OrderDetail> {

    /** Constructor – truyền OrderDetail.class cho GenericDAO */
    public OrderDetailDAO() {
        super(OrderDetail.class);
    }

    /**
     * Tìm tất cả chi tiết đơn hàng theo ID đơn hàng.
     *
     * LEFT JOIN FETCH od.product – tải eager thông tin sản phẩm
     * để có thể truy cập tên sản phẩm sau khi Session đóng.
     *
     * @param orderId ID đơn hàng cần xem chi tiết
     * @return danh sách OrderDetail kèm thông tin Product
     */
    public List<OrderDetail> findByOrderId(int orderId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM OrderDetail od LEFT JOIN FETCH od.product WHERE od.order.id = :oid",
                OrderDetail.class
            ).setParameter("oid", orderId).list();
        }
    }
}
