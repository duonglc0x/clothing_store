package com.clothingstore.dao;

import com.clothingstore.entity.Employee;

public class EmployeeDAO extends GenericDAO<Employee> {
    public EmployeeDAO() {
        super(Employee.class);
    }
}
