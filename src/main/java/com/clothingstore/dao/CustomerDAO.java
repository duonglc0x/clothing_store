package com.clothingstore.dao;

import com.clothingstore.entity.Customer;
import com.clothingstore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

/**
 * CustomerDAO – DAO cho entity Customer (Khách hàng).
 *
 * Kế thừa CRUD cơ bản từ GenericDAO, bổ sung thêm phương thức tìm kiếm theo tên.
 */
public class CustomerDAO extends GenericDAO<Customer> {

    /** Constructor – truyền Customer.class cho GenericDAO */
    public CustomerDAO() {
        super(Customer.class);
    }

    /**
     * Tìm kiếm khách hàng theo tên (LIKE – tìm gần đúng).
     *
     * HQL: "FROM Customer c WHERE c.fullName LIKE :keyword"
     * Thêm ký tự '%' trước và sau keyword để tìm chuỗi con.
     * VD: keyword="Nguyễn" → LIKE "%Nguyễn%" → tìm tất cả khách có "Nguyễn" trong tên.
     *
     * @param keyword từ khóa tìm kiếm
     * @return danh sách khách hàng phù hợp
     */
    public List<Customer> searchByName(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Customer c WHERE c.fullName LIKE :keyword", Customer.class
            ).setParameter("keyword", "%" + keyword + "%").list();
        }
    }
}
