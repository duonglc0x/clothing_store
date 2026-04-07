package com.clothingstore.service;

import com.clothingstore.dao.ProductDAO;
import com.clothingstore.dto.ProductDTO;
import com.clothingstore.entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductService – Tầng Service cho Sản phẩm.
 *
 * Ngoài CRUD cơ bản, lớp này có thêm phương thức toDTO() để chuyển đổi
 * Entity → DTO, tránh truyền Entity trực tiếp lên View (nguyên tắc separation of concerns).
 */
public class ProductService {

    private final ProductDAO productDAO = new ProductDAO();

    /** @return tất cả sản phẩm (không kèm thông tin liên quan) */
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    /**
     * Lấy tất cả sản phẩm KÈM Category và Supplier.
     * Sử dụng JOIN FETCH để tránh lỗi LazyInitializationException.
     */
    public List<Product> getAllProductsWithDetails() {
        return productDAO.findAllWithDetails();
    }

    /** @return sản phẩm theo ID */
    public Product getProductById(int id) {
        return productDAO.findById(id);
    }

    /** Thêm sản phẩm mới */
    public void addProduct(Product product) {
        productDAO.save(product);
    }

    /** Cập nhật thông tin sản phẩm */
    public void updateProduct(Product product) {
        productDAO.update(product);
    }

    /** Xóa sản phẩm theo ID (cascade xóa cả Inventory liên quan) */
    public void deleteProduct(int id) {
        productDAO.deleteById(id);
    }

    /**
     * Tìm kiếm sản phẩm theo tên.
     * @param keyword từ khóa
     * @return danh sách sản phẩm có tên chứa keyword
     */
    public List<Product> searchProducts(String keyword) {
        return productDAO.searchByName(keyword);
    }

    /** @return tổng số sản phẩm (dùng cho Dashboard) */
    public long countProducts() {
        return productDAO.count();
    }

    /**
     * Chuyển đổi danh sách Entity Product → danh sách DTO ProductDTO.
     *
     * Quá trình "flatten" (làm phẳng):
     * - Lấy tên danh mục từ Category entity → categoryName
     * - Lấy tên NCC từ Supplier entity → supplierName
     * - Tính tổng tồn kho từ tất cả Inventory → totalStock
     *
     * @param products danh sách entity Product
     * @return danh sách DTO để hiển thị trên View
     */
    public List<ProductDTO> toDTO(List<Product> products) {
        List<ProductDTO> dtos = new ArrayList<>();
        for (Product p : products) {
            ProductDTO dto = new ProductDTO();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setDescription(p.getDescription());
            dto.setPrice(p.getPrice());
            // Flatten quan hệ Category → lấy ID và tên
            dto.setCategoryId(p.getCategory() != null ? p.getCategory().getId() : null);
            dto.setCategoryName(p.getCategory() != null ? p.getCategory().getName() : "");
            // Flatten quan hệ Supplier → lấy ID và tên
            dto.setSupplierId(p.getSupplier() != null ? p.getSupplier().getId() : null);
            dto.setSupplierName(p.getSupplier() != null ? p.getSupplier().getName() : "");
            // Tính tổng tồn kho: cộng quantity của tất cả biến thể Inventory
            int totalStock = p.getInventories() != null
                    ? p.getInventories().stream().mapToInt(inv -> inv.getQuantity()).sum()
                    : 0;
            dto.setTotalStock(totalStock);
            dtos.add(dto);
        }
        return dtos;
    }
}
