package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;

import java.util.List;

public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public Employee registerEmployee(Employee employee) {
		return employeeService.registerEmployee(employee);
	}

	public Employee getEmployeeDetails(int id) {
		return employeeService.getEmployeeDetails(id);
	}

	public Employee updateEmployeeProfile(Employee employee) {
		return employeeService.updateEmployeeProfile(employee);
	}

	public List<Employee> listAllEmployees() {
		return employeeService.listAllEmployees();
	}
}