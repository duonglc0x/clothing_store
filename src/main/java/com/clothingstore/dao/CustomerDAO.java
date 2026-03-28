package com.clothingstore.dao;

import com.clothingstore.entity.Customer;
import com.clothingstore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class CustomerDAO extends GenericDAO<Customer> {

    public CustomerDAO() {
        super(Customer.class);
    }

    public List<Customer> searchByName(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Customer c WHERE c.fullName LIKE :keyword", Customer.class
            ).setParameter("keyword", "%" + keyword + "%").list();
        }
    }
}
