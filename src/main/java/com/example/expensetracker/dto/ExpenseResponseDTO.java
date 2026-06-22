package com.example.expensetracker.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ExpenseResponseDTO {

    private Long expenseId;
    private String title;
    private Double amount;
    private String category;
    private LocalDate expenseDate;
}