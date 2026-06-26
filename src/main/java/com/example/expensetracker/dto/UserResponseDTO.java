package com.example.expensetracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {

    private Long id;
    private String username;
    private String role;
}
