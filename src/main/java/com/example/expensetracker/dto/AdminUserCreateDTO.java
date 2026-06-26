package com.example.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminUserCreateDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Role is required")
    private String role;
}
