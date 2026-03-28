package com.clothingstore.service;

import com.clothingstore.dao.ProductDAO;
import com.clothingstore.dto.ProductDTO;
import com.clothingstore.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private final ProductDAO productDAO = new ProductDAO();

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public List<Product> getAllProductsWithDetails() {
        return productDAO.findAllWithDetails();
    }

    public Product getProductById(int id) {
        return productDAO.findById(id);
    }

    public void addProduct(Product product) {
        productDAO.save(product);
    }

    public void updateProduct(Product product) {
        productDAO.update(product);
    }

    public void deleteProduct(int id) {
        productDAO.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return productDAO.searchByName(keyword);
    }

    public long countProducts() {
        return productDAO.count();
    }

    /**
     * Chuyển đổi Entity → DTO để hiển thị trên View.
     */
    public List<ProductDTO> toDTO(List<Product> products) {
        List<ProductDTO> dtos = new ArrayList<>();
        for (Product p : products) {
            ProductDTO dto = new ProductDTO();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setDescription(p.getDescription());
            dto.setPrice(p.getPrice());
            dto.setCategoryId(p.getCategory() != null ? p.getCategory().getId() : null);
            dto.setCategoryName(p.getCategory() != null ? p.getCategory().getName() : "");
            dto.setSupplierId(p.getSupplier() != null ? p.getSupplier().getId() : null);
            dto.setSupplierName(p.getSupplier() != null ? p.getSupplier().getName() : "");
            // Tính tổng tồn kho
            int totalStock = p.getInventories() != null
                    ? p.getInventories().stream().mapToInt(inv -> inv.getQuantity()).sum()
                    : 0;
            dto.setTotalStock(totalStock);
            dtos.add(dto);
        }
        return dtos;
    }
}
