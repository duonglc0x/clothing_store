package com.clothingstore.dao;

import com.clothingstore.entity.Product;
import com.clothingstore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

/**
 * ProductDAO – DAO cho entity Product (Sản phẩm).
 *
 * Kế thừa CRUD từ GenericDAO, bổ sung:
 * - findAllWithDetails(): tải sản phẩm kèm Category và Supplier (JOIN FETCH)
 * - searchByName(): tìm kiếm theo tên sản phẩm
 */
public class ProductDAO extends GenericDAO<Product> {

    /** Constructor – truyền Product.class cho GenericDAO */
    public ProductDAO() {
        super(Product.class);
    }

    /**
     * Lấy tất cả sản phẩm kèm theo Category và Supplier.
     *
     * Sử dụng LEFT JOIN FETCH để tải eager (tải sẵn) các quan hệ Lazy,
     * tránh lỗi LazyInitializationException khi truy cập category/supplier
     * sau khi Session đã đóng.
     *
     * SELECT DISTINCT p – loại bỏ bản ghi trùng lặp do JOIN.
     *
     * @return danh sách sản phẩm với đầy đủ thông tin category và supplier
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
     * Tìm kiếm sản phẩm theo tên (LIKE – tìm gần đúng).
     *
     * @param keyword từ khóa tìm kiếm
     * @return danh sách sản phẩm có tên chứa keyword
     */
    public List<Product> searchByName(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Product p WHERE p.name LIKE :keyword", Product.class
            ).setParameter("keyword", "%" + keyword + "%").list();
        }
    }
}
