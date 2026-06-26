package com.example.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminUserUpdateDTO {

    @NotBlank(message = "Username is required")
    private String username;

    private String password;

    @NotBlank(message = "Role is required")
    private String role;
}
