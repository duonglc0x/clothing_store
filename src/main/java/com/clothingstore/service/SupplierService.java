package com.clothingstore.service;

import com.clothingstore.dao.SupplierDAO;
import com.clothingstore.entity.Supplier;

import java.util.List;

public class SupplierService {

    private final SupplierDAO supplierDAO = new SupplierDAO();

    public List<Supplier> getAllSuppliers() {
        return supplierDAO.findAll();
    }

    public Supplier getSupplierById(int id) {
        return supplierDAO.findById(id);
    }

    public void addSupplier(Supplier supplier) {
        supplierDAO.save(supplier);
    }

    public void updateSupplier(Supplier supplier) {
        supplierDAO.update(supplier);
    }

    public void deleteSupplier(int id) {
        supplierDAO.deleteById(id);
    }
}
