package com.clothingstore.dao;

import com.clothingstore.entity.Product;
import com.clothingstore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class ProductDAO extends GenericDAO<Product> {

    public ProductDAO() {
        super(Product.class);
    }

    /**
     * Lấy tất cả sản phẩm kèm theo Category và Supplier (JOIN FETCH).
     */
    public List<Product> findAllWithDetails() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "SELECT DISTINCT p FROM Product p " +
                "LEFT JOIN FETCH p.category " +
                "LEFT JOIN FETCH p.supplier", Product.class
            ).list();
        }
    }

    /**
     * Tìm sản phẩm theo tên (LIKE).
     */
    public List<Product> searchByName(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Product p WHERE p.name LIKE :keyword", Product.class
            ).setParameter("keyword", "%" + keyword + "%").list();
        }
    }
}
