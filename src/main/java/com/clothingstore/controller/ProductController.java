package com.clothingstore.controller;

import com.clothingstore.entity.Category;
import com.clothingstore.entity.Product;
import com.clothingstore.entity.Supplier;
import com.clothingstore.service.CategoryService;
import com.clothingstore.service.ProductService;
import com.clothingstore.service.SupplierService;

import java.math.BigDecimal;
import java.util.List;

public class ProductController {

    private final ProductService productService = new ProductService();
    private final CategoryService categoryService = new CategoryService();
    private final SupplierService supplierService = new SupplierService();

    public List<Product> getAllProducts() {
        return productService.getAllProductsWithDetails();
    }

    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    public void addProduct(String name, String description, BigDecimal price,
                           int categoryId, Integer supplierId) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(categoryService.getCategoryById(categoryId));
        if (supplierId != null) {
            product.setSupplier(supplierService.getSupplierById(supplierId));
        }
        productService.addProduct(product);
    }

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
                product.setSupplier(null);
            }
            productService.updateProduct(product);
        }
    }

    public void deleteProduct(int id) {
        productService.deleteProduct(id);
    }

    public long countProducts() {
        return productService.countProducts();
    }
}
