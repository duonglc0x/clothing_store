package com.clothingstore.service;

import com.clothingstore.dao.InventoryDAO;
import com.clothingstore.entity.Inventory;

import java.util.List;

public class InventoryService {

    private final InventoryDAO inventoryDAO = new InventoryDAO();

    public List<Inventory> getAll() {
        return inventoryDAO.findAll();
    }

    public List<Inventory> getByProductId(int productId) {
        return inventoryDAO.findByProductId(productId);
    }

    public void addInventory(Inventory inventory) {
        inventoryDAO.save(inventory);
    }

    public void updateInventory(Inventory inventory) {
        inventoryDAO.update(inventory);
    }

    public void deleteInventory(int id) {
        inventoryDAO.deleteById(id);
    }
}
