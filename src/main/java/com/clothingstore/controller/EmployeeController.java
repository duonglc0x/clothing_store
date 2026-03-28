package com.clothingstore.controller;

import com.clothingstore.entity.Employee;
import com.clothingstore.service.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class EmployeeController {

    private final EmployeeService employeeService = new EmployeeService();

    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    public void addEmployee(String fullName, String phone, String email,
                            String position, BigDecimal salary, LocalDate hireDate) {
        Employee e = new Employee();
        e.setFullName(fullName);
        e.setPhone(phone);
        e.setEmail(email);
        e.setPosition(position);
        e.setSalary(salary != null ? salary : BigDecimal.ZERO);
        e.setHireDate(hireDate);
        employeeService.addEmployee(e);
    }

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

    public void deleteEmployee(int id) {
        employeeService.deleteEmployee(id);
    }

    public long countEmployees() {
        return employeeService.countEmployees();
    }
}
