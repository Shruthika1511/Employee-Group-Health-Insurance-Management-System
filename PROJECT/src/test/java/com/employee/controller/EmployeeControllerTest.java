package com.employee.controller;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;
import com.exception.EmployeeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee(null, "shruthika", "shruthika@example.com", "1234567890", "123 Street", "Developer", 101L);
    }

    @Test
    public void testRegisterEmployee_SuccessfullyRegistered() {
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(true);

        boolean result = employeeController.registerEmployee(employee);

        //assertTrue(result);
        verify(employeeService, times(1)).saveEmployee(employee);
    }

    @Test
    void _EmployeeIdIsNotNull() {
        employee.setEmployeeId(1L);

        EmployeeException exception = assertThrows(EmployeeException.class, () -> {
            employeeController.registerEmployee(employee);
        });

        assertEquals("Employee ID should be null when registering a new employee.", exception.getMessage());
    }

    @Test
    public void testRegisterEmployee_ExistingEmail() {
        when(employeeService.emailExists(anyString())).thenReturn(true);

        EmployeeException exception = assertThrows(EmployeeException.class, () -> {
            employeeController.registerEmployee(employee);
        });

        assertEquals("Email already exists.", exception.getMessage());
    }

    @Test
    public void testRegisterEmployee_InvalidEmail() {
        employee.setEmail("invalid-email");

        EmployeeException exception = assertThrows(EmployeeException.class, () -> {
            employeeController.registerEmployee(employee);
        });

        assertEquals("Invalid email format.", exception.getMessage());
    }

    @Test
    public void testRegisterEmployee_InvalidPhoneNumber() {
        employee.setPhone("123");

        EmployeeException exception = assertThrows(EmployeeException.class, () -> {
            employeeController.registerEmployee(employee);
        });

        assertEquals("Invalid phone number.", exception.getMessage());
    }

    @Test
    public void testRegisterEmployee_InvalidDesignation() {
        employee.setDesignation("");

        EmployeeException exception = assertThrows(EmployeeException.class, () -> {
            employeeController.registerEmployee(employee);
        });

        assertEquals("Designation is required.", exception.getMessage());
    }

    @Test
    public void testRegisterEmployee_InvalidOrganizationId() {
        employee.setOrganizationId(null);

        EmployeeException exception = assertThrows(EmployeeException.class, () -> {
            employeeController.registerEmployee(employee);
        });

        assertEquals("Organization ID is required.", exception.getMessage());
    }

    @Test
    public void testGetEmployeeDetails_ValidEmployeeId() {
        when(employeeService.findEmployeeById(anyLong())).thenReturn(true);

        Employee result = employeeController.getEmployeeDetails(1L);

        assertNotNull(result);
    }

    @Test
    public void testGetEmployeeDetails_InvalidEmployeeId() {
        when(employeeService.findEmployeeById(anyLong())).thenReturn(false);

        EmployeeException exception = assertThrows(EmployeeException.class, () -> {
            employeeController.getEmployeeDetails(1L);
        });

        assertEquals("Employee not found.", exception.getMessage());
    }

    @Test
    public void testUpdateEmployeeProfile_SuccessfullyUpdated() {
        employee.setEmployeeId(1L); // Ensure employeeId is set for update
        when(employeeService.updateEmployee(any(Employee.class))).thenReturn(true);

        Employee result = employeeController.updateEmployeeProfile(employee);

        assertNotNull(result);
        verify(employeeService, times(1)).updateEmployee(employee);
    }

    @Test
    public void testUpdateEmployeeProfile_InvalidPhoneNumber() {
        employee.setEmployeeId(1L); // Ensure employeeId is set for update
        employee.setPhone("123");

        EmployeeException exception = assertThrows(EmployeeException.class, () -> {
            employeeController.updateEmployeeProfile(employee);
        });

        assertEquals("Invalid phone number.", exception.getMessage());
    }

    @Test
    public void testUpdateEmployeeProfile_InvalidAddress() {
        employee.setEmployeeId(1L); // Ensure employeeId is set for update
        employee.setAddress("");

        EmployeeException exception = assertThrows(EmployeeException.class, () -> {
            employeeController.updateEmployeeProfile(employee);
        });

        assertEquals("Address is required.", exception.getMessage());
    }

    @Test
    public void testUpdateEmployeeProfile_EmployeeNotFound() {
        employee.setEmployeeId(1L); // Ensure employeeId is set for update
        when(employeeService.updateEmployee(any(Employee.class))).thenReturn(false);

        EmployeeException exception = assertThrows(EmployeeException.class, () -> {
            employeeController.updateEmployeeProfile(employee);
        });

        assertEquals("Employee not found.", exception.getMessage());
    }

    @Test
    public void testListAllEmployees() {
        when(employeeService.listAllEmployees()).thenReturn(true);

        boolean result = employeeController.listAllEmployees();

        assertTrue(result);
        verify(employeeService, times(1)).listAllEmployees();
    }
    @Test
    public void testListAllEmployees_InvalidRequest() {
        when(employeeService.listAllEmployees()).thenReturn(false);

        EmployeeException exception = assertThrows(EmployeeException.class, () -> {
            employeeController.listAllEmployees();
        });

        assertEquals("No employees found.", exception.getMessage());
    }

    @Test
    public void testRegisterEmployee_EmptyFields() {
        Employee emptyEmployee = new Employee(null, null, null, "1234567890", "123 Street", "Developer", 101L);

        EmployeeException exception = assertThrows(EmployeeException.class, () -> {
            employeeController.registerEmployee(emptyEmployee);
        });

        assertEquals("Fields cannot be empty.", exception.getMessage());
    }

    @Test
    public void testGetEmployeeDetails_EmptyEmployeeId() {
        EmployeeException exception = assertThrows(EmployeeException.class, () -> {
            employeeController.getEmployeeDetails(null);
        });

        assertEquals("Employee ID cannot be null.", exception.getMessage());
    }

    @Test
    public void testUpdateEmployeeProfile_EmptyFields() {
        Employee emptyEmployee = new Employee(null, null, null, "1234567890", "123 Street", "Developer", 101L);

        EmployeeException exception = assertThrows(EmployeeException.class, () -> {
            employeeController.registerEmployee(emptyEmployee);
        });

        assertEquals("Fields cannot be empty.", exception.getMessage());
    }
    @Test
    public void testListAllEmployeesEmpty() {
        // Mock the EmployeeService to return an empty result
        when(employeeService.listAllEmployees()).thenReturn(new EmployeeController.EmptyResult().isEmpty());

        // Call the method
        boolean result = employeeController.listAllEmployees();

        // Assert that the result is empty
        assertTrue(result, "Empty list of employees");
    }

    @Test
    public void testGetEmployeeDetailsWithNullEmail() {
        // Assert that an exception is thrown when email is null
        assertThrows(EmployeeException.class, () -> {
            employeeController.getEmployeeDetails(null);
        }, "Email cannot be null or empty.");
    }
}