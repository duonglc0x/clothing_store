package com.clothingstore.service;

import com.clothingstore.dao.InventoryDAO;
import com.clothingstore.entity.Inventory;

import java.util.List;

/**
 * InventoryService – Tầng Service cho Tồn kho.
 *
 * Cung cấp CRUD và truy vấn tồn kho theo sản phẩm.
 */
public class InventoryService {

    private final InventoryDAO inventoryDAO = new InventoryDAO();

    /** @return danh sách tất cả bản ghi tồn kho */
    public List<Inventory> getAll() {
        return inventoryDAO.findAll();
    }

    /**
     * Lấy tồn kho theo sản phẩm.
     * @param productId ID sản phẩm
     * @return danh sách biến thể tồn kho (size/color) của sản phẩm đó
     */
    public List<Inventory> getByProductId(int productId) {
        return inventoryDAO.findByProductId(productId);
    }

    /** Thêm bản ghi tồn kho mới */
    public void addInventory(Inventory inventory) {
        inventoryDAO.save(inventory);
    }

    /** Cập nhật số lượng tồn kho */
    public void updateInventory(Inventory inventory) {
        inventoryDAO.update(inventory);
    }

    /** Xóa bản ghi tồn kho theo ID */
    public void deleteInventory(int id) {
        inventoryDAO.deleteById(id);
    }
}
