package com.clothingstore.dao;

import com.clothingstore.entity.Inventory;
import com.clothingstore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

/**
 * InventoryDAO – DAO cho entity Inventory (Tồn kho).
 *
 * Kế thừa CRUD từ GenericDAO, bổ sung truy vấn tìm tồn kho theo sản phẩm.
 */
public class InventoryDAO extends GenericDAO<Inventory> {

    /** Constructor – truyền Inventory.class cho GenericDAO */
    public InventoryDAO() {
        super(Inventory.class);
    }

    /**
     * Tìm tất cả bản ghi tồn kho theo ID sản phẩm.
     *
     * HQL: "FROM Inventory i WHERE i.product.id = :pid"
     * Trả về danh sách các biến thể (size/color) của sản phẩm cùng số lượng tồn kho.
     *
     * @param productId ID sản phẩm cần tra cứu
     * @return danh sách Inventory (các biến thể size/color)
     */
    public List<Inventory> findByProductId(int productId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Inventory i WHERE i.product.id = :pid", Inventory.class
            ).setParameter("pid", productId).list();
        }
    }
}
