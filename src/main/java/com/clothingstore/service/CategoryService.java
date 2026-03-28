package com.clothingstore.service;

import com.clothingstore.dao.CategoryDAO;
import com.clothingstore.entity.Category;

import java.util.List;

public class CategoryService {

    private final CategoryDAO categoryDAO = new CategoryDAO();

    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    public Category getCategoryById(int id) {
        return categoryDAO.findById(id);
    }

    public void addCategory(Category category) {
        categoryDAO.save(category);
    }

    public void updateCategory(Category category) {
        categoryDAO.update(category);
    }

    public void deleteCategory(int id) {
        categoryDAO.deleteById(id);
    }

    public long countCategories() {
        return categoryDAO.count();
    }
}
