package com.example.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExpenseCategoryUpdateDTO {

    @NotBlank(message = "Category is required")
    private String category;
}
