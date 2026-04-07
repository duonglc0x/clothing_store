package com.clothingstore.controller;

import com.clothingstore.entity.Employee;
import com.clothingstore.service.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * EmployeeController – Tầng Controller cho Nhân viên.
 *
 * Nhận dữ liệu thô từ View (String, BigDecimal, LocalDate),
 * tạo/cập nhật Entity, gọi Service xử lý.
 */
public class EmployeeController {

    /** Service xử lý nghiệp vụ nhân viên */
    private final EmployeeService employeeService = new EmployeeService();

    /** @return danh sách tất cả nhân viên */
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    /**
     * Thêm nhân viên mới.
     *
     * @param fullName họ tên
     * @param phone    SĐT
     * @param email    email
     * @param position chức vụ
     * @param salary   mức lương (null → mặc định BigDecimal.ZERO)
     * @param hireDate ngày vào làm (có thể null)
     */
    public void addEmployee(String fullName, String phone, String email,
                            String position, BigDecimal salary, LocalDate hireDate) {
        Employee e = new Employee();
        e.setFullName(fullName);
        e.setPhone(phone);
        e.setEmail(email);
        e.setPosition(position);
        e.setSalary(salary != null ? salary : BigDecimal.ZERO);  // Xử lý null → 0
        e.setHireDate(hireDate);
        employeeService.addEmployee(e);
    }

    /**
     * Cập nhật thông tin nhân viên.
     * Tìm entity cũ → cập nhật → lưu.
     */
    public void updateEmployee(int id, String fullName, String phone, String email,
                               String position, BigDecimal salary, LocalDate hireDate) {
        Employee e = employeeService.getEmployeeById(id);
        if (e != null) {
            e.setFullName(fullName);
            e.setPhone(phone);
            e.setEmail(email);
            e.setPosition(position);
            e.setSalary(salary != null ? salary : BigDecimal.ZERO);
            e.setHireDate(hireDate);
            employeeService.updateEmployee(e);
        }
    }

    /** Xóa nhân viên theo ID */
    public void deleteEmployee(int id) {
        employeeService.deleteEmployee(id);
    }

    /** @return tổng số nhân viên */
    public long countEmployees() {
        return employeeService.countEmployees();
    }
}
