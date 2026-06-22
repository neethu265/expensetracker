package com.example.expensetracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseDashboardDTO {

    private Double totalSpent;

    private Double averageExpense;

    private Long transactionCount;
}