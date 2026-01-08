package com.example.userservice.entity;

import com.example.userservice.enums.EmployeeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Employee extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID employeeId;

    @NotNull(message = "Account ID cannot be null")
    @Column(name = "account_id", unique = true, nullable = false)
    private UUID accountId;

    @NotBlank(message = "Employee code cannot be blank")
    @Size(min = 3, max = 20, message = "Employee code must be between 3 and 20 characters")
    @Column(name = "employee_code", unique = true, nullable = false)
    private String employeeCode;

    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column
    private Boolean isManager;

    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0")
    @Column(precision = 15, scale = 2)
    private BigDecimal salary;

    @PastOrPresent(message = "Hire date cannot be in the future")
    @Column(name = "hire_date")
    private LocalDate hireDate;

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;
}
