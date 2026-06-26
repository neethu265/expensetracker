package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseDashboardDTO;
import com.example.expensetracker.dto.ExpenseRequestDTO;
import com.example.expensetracker.dto.ExpenseResponseDTO;

import java.util.List;

public interface ExpenseService {

    ExpenseResponseDTO addExpense(ExpenseRequestDTO dto);

    ExpenseResponseDTO getExpenseById(Long id);

    List<ExpenseResponseDTO> getAllExpenses();

    ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO dto);

    List<ExpenseResponseDTO> getExpensesByCategory(String category);

    ExpenseDashboardDTO getDashboard();

    List<ExpenseResponseDTO> getExpensesByOffset(int offset, int limit);

    void deleteExpense(Long id);
}