package com.employee.controller;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;
import com.exception.EmployeeException;

public class EmployeeController {

    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public boolean registerEmployee(Employee employee) {
        if (employee.getName() == null || employee.getEmail() == null) {
            throw new EmployeeException("Fields cannot be empty.");
        }
        if (employee.getEmployeeId() != null) {
            throw new EmployeeException("Employee ID should be null when registering a new employee.");
        }
        if (employeeService.emailExists(employee.getEmail())) {
            throw new EmployeeException("Email already exists.");
        }
        if (!employee.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new EmployeeException("Invalid email format.");
        }
        if (!employee.getPhone().matches("\\d{10}")) {
            throw new EmployeeException("Invalid phone number.");
        }
        if (employee.getDesignation() == null || employee.getDesignation().isEmpty()) {
            throw new EmployeeException("Designation is required.");
        }
        if (employee.getOrganizationId() == null) {
            throw new EmployeeException("Organization ID is required.");
        }
        return employeeService.saveEmployee(employee);
    }

    public Employee getEmployeeDetails(Long employeeId) {
        if (employeeId == null) {
            throw new EmployeeException("Employee ID cannot be null.");
        }
        if (!employeeService.findEmployeeById(employeeId)) {
            throw new EmployeeException("Employee not found.");
        }
        return new Employee(employeeId, "Dummy Name", "dummy.email@example.com", "1234567890", "Dummy Address", "Dummy Designation", 1L);
    }

    public Employee updateEmployeeProfile(Employee employee) {
        if (employee.getEmployeeId() == null) {
            throw new EmployeeException("Employee ID is required.");
        }
        if (!employee.getPhone().matches("\\d{10}")) {
            throw new EmployeeException("Invalid phone number.");
        }
        if (employee.getAddress() == null || employee.getAddress().isEmpty()) {
            throw new EmployeeException("Address is required.");
        }
        if (!employeeService.updateEmployee(employee)) {
            throw new EmployeeException("Employee not found.");
        }
        return employee;
    }

    public boolean listAllEmployees() throws EmployeeException {
        boolean result = employeeService.listAllEmployees();
        if (!result) {
            throw new EmployeeException("No employees found.");
        }
        return result;
    }
    public static class EmptyResult {
        public boolean isEmpty() {
            return true;
        }
    }
}