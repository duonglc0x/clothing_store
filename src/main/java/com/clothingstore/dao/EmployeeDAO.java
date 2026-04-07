package com.clothingstore.dao;

import com.clothingstore.entity.Employee;

/**
 * EmployeeDAO – DAO cho entity Employee (Nhân viên).
 *
 * Kế thừa CRUD cơ bản từ GenericDAO<Employee>.
 */
public class EmployeeDAO extends GenericDAO<Employee> {

    /** Constructor – truyền Employee.class cho GenericDAO */
    public EmployeeDAO() {
        super(Employee.class);
    }
}
