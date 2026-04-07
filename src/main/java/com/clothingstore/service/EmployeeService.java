package com.clothingstore.service;

import com.clothingstore.dao.EmployeeDAO;
import com.clothingstore.entity.Employee;

import java.util.List;

/**
 * EmployeeService – Tầng Service cho Nhân viên.
 *
 * Cung cấp CRUD cơ bản cho quản lý nhân viên.
 */
public class EmployeeService {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    /** @return danh sách tất cả nhân viên */
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    /** @return nhân viên theo ID */
    public Employee getEmployeeById(int id) {
        return employeeDAO.findById(id);
    }

    /** Thêm nhân viên mới */
    public void addEmployee(Employee employee) {
        employeeDAO.save(employee);
    }

    /** Cập nhật thông tin nhân viên */
    public void updateEmployee(Employee employee) {
        employeeDAO.update(employee);
    }

    /** Xóa nhân viên theo ID */
    public void deleteEmployee(int id) {
        employeeDAO.deleteById(id);
    }

    /** @return tổng số nhân viên (dùng cho Dashboard) */
    public long countEmployees() {
        return employeeDAO.count();
    }
}
