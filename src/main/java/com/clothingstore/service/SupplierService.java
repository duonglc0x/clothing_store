package com.clothingstore.service;

import com.clothingstore.dao.SupplierDAO;
import com.clothingstore.entity.Supplier;

import java.util.List;

/**
 * SupplierService – Tầng Service cho Nhà cung cấp.
 *
 * Cung cấp CRUD cơ bản cho quản lý nhà cung cấp.
 */
public class SupplierService {

    private final SupplierDAO supplierDAO = new SupplierDAO();

    /** @return danh sách tất cả nhà cung cấp */
    public List<Supplier> getAllSuppliers() {
        return supplierDAO.findAll();
    }

    /** @return nhà cung cấp theo ID */
    public Supplier getSupplierById(int id) {
        return supplierDAO.findById(id);
    }

    /** Thêm nhà cung cấp mới */
    public void addSupplier(Supplier supplier) {
        supplierDAO.save(supplier);
    }

    /** Cập nhật thông tin nhà cung cấp */
    public void updateSupplier(Supplier supplier) {
        supplierDAO.update(supplier);
    }

    /** Xóa nhà cung cấp theo ID */
    public void deleteSupplier(int id) {
        supplierDAO.deleteById(id);
    }
}
