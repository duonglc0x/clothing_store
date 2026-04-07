package com.clothingstore.dao;

import com.clothingstore.entity.Supplier;

/**
 * SupplierDAO – DAO cho entity Supplier (Nhà cung cấp).
 *
 * Kế thừa CRUD cơ bản từ GenericDAO<Supplier>.
 */
public class SupplierDAO extends GenericDAO<Supplier> {

    /** Constructor – truyền Supplier.class cho GenericDAO */
    public SupplierDAO() {
        super(Supplier.class);
    }
}
