package com.employee.model;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@ToString

public class Employee {
    private Long employeeId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String designation;
    private Long organizationId;
}