package com.example.userservice.dto;

import com.example.userservice.enums.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private UUID employeeId;
    private UUID accountId;
    private String employeeCode;
    private String fullName;
    private String email;
    private Boolean isManager;
    private BigDecimal salary;
    private LocalDate hireDate;
    private EmployeeStatus status;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
