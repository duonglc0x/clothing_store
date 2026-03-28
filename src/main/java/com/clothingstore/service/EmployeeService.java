package com.clothingstore.service;

import com.clothingstore.dao.EmployeeDAO;
import com.clothingstore.entity.Employee;

import java.util.List;

public class EmployeeService {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    public Employee getEmployeeById(int id) {
        return employeeDAO.findById(id);
    }

    public void addEmployee(Employee employee) {
        employeeDAO.save(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeDAO.update(employee);
    }

    public void deleteEmployee(int id) {
        employeeDAO.deleteById(id);
    }

    public long countEmployees() {
        return employeeDAO.count();
    }
}
