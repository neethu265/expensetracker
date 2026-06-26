package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseDashboardDTO;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository repository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;


    @Test
    void shouldCalculateDashboardCorrectly() {

        Expense e1 = Expense.builder()
                .expenseId(1L)
                .title("Food")
                .amount(100.0)
                .category("Food")
                .expenseDate(LocalDate.now())
                .build();

        Expense e2 = Expense.builder()
                .expenseId(2L)
                .title("Movie")
                .amount(200.0)
                .category("Entertainment")
                .expenseDate(LocalDate.now())
                .build();

        Expense e3 = Expense.builder()
                .expenseId(3L)
                .title("Petrol")
                .amount(300.0)
                .category("Travel")
                .expenseDate(LocalDate.now())
                .build();

        when(repository.findAll())
                .thenReturn(List.of(e1, e2, e3));

        ExpenseDashboardDTO dashboard =
                expenseService.getDashboard();

        assertEquals(600, dashboard.getTotalSpent());
        assertEquals(200, dashboard.getAverageExpense());
        assertEquals(3, dashboard.getTransactionCount());
    }


    @Test
    void shouldReturnZeroWhenNoExpensesExist() {

        when(repository.findAll())
                .thenReturn(List.of());

        ExpenseDashboardDTO dashboard =
                expenseService.getDashboard();

        assertEquals(0, dashboard.getTotalSpent());
        assertEquals(0, dashboard.getAverageExpense());
        assertEquals(0, dashboard.getTransactionCount());
    }
}