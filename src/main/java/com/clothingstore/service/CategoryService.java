package com.clothingstore.service;

import com.clothingstore.dao.CategoryDAO;
import com.clothingstore.entity.Category;

import java.util.List;

/**
 * CategoryService – Tầng Service cho Danh mục sản phẩm.
 *
 * Tầng Service (Business Logic Layer) nằm giữa Controller và DAO:
 * - Nhận yêu cầu từ Controller
 * - Thực hiện logic nghiệp vụ (validation, tính toán...)
 * - Gọi DAO để truy cập CSDL
 *
 * Hiện tại các phương thức chỉ delegate (ủy quyền) trực tiếp cho DAO.
 * Khi cần thêm logic nghiệp vụ (VD: kiểm tra tên trùng, không xóa danh mục
 * đang có sản phẩm), sẽ bổ sung tại tầng này.
 */
public class CategoryService {

    /** Khởi tạo DAO – mỗi Service có một DAO tương ứng */
    private final CategoryDAO categoryDAO = new CategoryDAO();

    /** @return danh sách tất cả danh mục */
    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    /** @return danh mục theo ID, null nếu không tìm thấy */
    public Category getCategoryById(int id) {
        return categoryDAO.findById(id);
    }

    /** Thêm danh mục mới vào CSDL */
    public void addCategory(Category category) {
        categoryDAO.save(category);
    }

    /** Cập nhật thông tin danh mục */
    public void updateCategory(Category category) {
        categoryDAO.update(category);
    }

    /** Xóa danh mục theo ID */
    public void deleteCategory(int id) {
        categoryDAO.deleteById(id);
    }

    /** @return tổng số danh mục (dùng cho Dashboard) */
    public long countCategories() {
        return categoryDAO.count();
    }
}
