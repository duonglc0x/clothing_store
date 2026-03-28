package com.clothingstore.dao;

import com.clothingstore.entity.Inventory;
import com.clothingstore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class InventoryDAO extends GenericDAO<Inventory> {

    public InventoryDAO() {
        super(Inventory.class);
    }

    public List<Inventory> findByProductId(int productId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Inventory i WHERE i.product.id = :pid", Inventory.class
            ).setParameter("pid", productId).list();
        }
    }
}
