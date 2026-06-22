package com.example.expensetracker.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CursorExpenseResponseDTO {

    private List<ExpenseResponseDTO> expenses;

    private Long nextCursor;

    private boolean hasNext;
}