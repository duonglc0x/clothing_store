package com.clothingstore.dao;

import com.clothingstore.entity.Category;

/**
 * CategoryDAO – DAO cho entity Category (Danh mục).
 *
 * Kế thừa GenericDAO<Category> nên tự động có các phương thức CRUD:
 * findById, findAll, save, update, delete, deleteById, count.
 *
 * Nếu cần thêm truy vấn riêng (VD: tìm theo tên), thêm phương thức tại đây.
 */
public class CategoryDAO extends GenericDAO<Category> {

    /** Constructor – truyền Category.class cho GenericDAO để biết ánh xạ bảng nào */
    public CategoryDAO() {
        super(Category.class);
    }
}
