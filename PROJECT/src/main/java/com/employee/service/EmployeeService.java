package com.employee.service;

import com.employee.model.Employee;

//import java.util.Optional;

public class EmployeeService {

    public boolean emailExists(String email) {
        return false;
    }

    public boolean saveEmployee(Employee employee) {
        return true;
    }

    public boolean findEmployeeById(Long employeeId) {
        return false;
    }

    public boolean updateEmployee(Employee employee) {
        return true;
    }

    public boolean listAllEmployees() {
        return true;
    }
}