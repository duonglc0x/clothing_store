package com.clothingstore.controller;

import com.clothingstore.entity.Category;
import com.clothingstore.entity.Product;
import com.clothingstore.entity.Supplier;
import com.clothingstore.service.CategoryService;
import com.clothingstore.service.ProductService;
import com.clothingstore.service.SupplierService;

import java.math.BigDecimal;
import java.util.List;

/**
 * ProductController – Tầng Controller cho Sản phẩm.
 *
 * Phối hợp 3 Service: ProductService, CategoryService, SupplierService
 * vì khi thêm/sửa sản phẩm cần chọn danh mục và nhà cung cấp.
 */
public class ProductController {

    private final ProductService productService = new ProductService();
    private final CategoryService categoryService = new CategoryService();
    private final SupplierService supplierService = new SupplierService();

    /**
     * Lấy tất cả sản phẩm kèm thông tin Category và Supplier.
     * Sử dụng JOIN FETCH để tránh LazyInitializationException.
     */
    public List<Product> getAllProducts() {
        return productService.getAllProductsWithDetails();
    }

    /** @return danh sách tất cả danh mục (dùng để fill JComboBox khi thêm/sửa) */
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /** @return danh sách tất cả nhà cung cấp (dùng cho JComboBox) */
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    /**
     * Thêm sản phẩm mới.
     * Tìm Category và Supplier theo ID, gán vào Entity, lưu CSDL.
     *
     * @param name        tên sản phẩm
     * @param description mô tả
     * @param price       giá bán
     * @param categoryId  ID danh mục (bắt buộc)
     * @param supplierId  ID nhà cung cấp (có thể null)
     */
    public void addProduct(String name, String description, BigDecimal price,
                           int categoryId, Integer supplierId) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        // Tìm và gán entity Category theo ID
        product.setCategory(categoryService.getCategoryById(categoryId));
        // Gán Supplier nếu có
        if (supplierId != null) {
            product.setSupplier(supplierService.getSupplierById(supplierId));
        }
        productService.addProduct(product);
    }

    /**
     * Cập nhật sản phẩm.
     * Tìm entity cũ → cập nhật các trường → lưu.
     */
    public void updateProduct(int id, String name, String description, BigDecimal price,
                              int categoryId, Integer supplierId) {
        Product product = productService.getProductById(id);
        if (product != null) {
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setCategory(categoryService.getCategoryById(categoryId));
            if (supplierId != null) {
                product.setSupplier(supplierService.getSupplierById(supplierId));
            } else {
                product.setSupplier(null);  // Xóa Supplier nếu không chọn
            }
            productService.updateProduct(product);
        }
    }

    /** Xóa sản phẩm theo ID */
    public void deleteProduct(int id) {
        productService.deleteProduct(id);
    }

    /** @return tổng số sản phẩm */
    public long countProducts() {
        return productService.countProducts();
    }
}
