package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseDashboardDTO;
import com.example.expensetracker.dto.ExpenseRequestDTO;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.entity.User;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository repository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @BeforeEach
    void setUpAuthentication() {

        SecurityContextHolder
                .getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                "testuser",
                                null));
    }

    @AfterEach
    void clearAuthentication() {

        SecurityContextHolder.clearContext();
    }

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

        when(repository.findByUserUsername("testuser"))
                .thenReturn(List.of(e1, e2, e3));

        ExpenseDashboardDTO dashboard =
                expenseService.getDashboard();

        assertEquals(600, dashboard.getTotalSpent());
        assertEquals(200, dashboard.getAverageExpense());
        assertEquals(3, dashboard.getTransactionCount());
    }

    @Test
    void shouldAttachExpenseToAuthenticatedUser() {

        User user = User.builder()
                .id(10L)
                .username("testuser")
                .password("password")
                .role("ROLE_USER")
                .build();

        ExpenseRequestDTO request = new ExpenseRequestDTO();
        request.setTitle("Lunch");
        request.setAmount(150.0);
        request.setCategory("Food");

        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(user));
        when(repository.save(any(Expense.class)))
                .thenAnswer(invocation -> {
                    Expense expense = invocation.getArgument(0);
                    expense.setExpenseId(1L);
                    return expense;
                });

        var response = expenseService.addExpense(request);

        ArgumentCaptor<Expense> expenseCaptor =
                ArgumentCaptor.forClass(Expense.class);

        assertEquals(1L, response.getExpenseId());
        assertEquals("Lunch", response.getTitle());
        verify(repository).save(expenseCaptor.capture());
        assertEquals(user, expenseCaptor.getValue().getUser());
    }


    @Test
    void shouldReturnZeroWhenNoExpensesExist() {

        when(repository.findByUserUsername("testuser"))
                .thenReturn(List.of());

        ExpenseDashboardDTO dashboard =
                expenseService.getDashboard();

        assertEquals(0, dashboard.getTotalSpent());
        assertEquals(0, dashboard.getAverageExpense());
        assertEquals(0, dashboard.getTransactionCount());
    }

    @Test
    void shouldUpdateExpenseCategoryOnly() {

        Expense expense = Expense.builder()
                .expenseId(1L)
                .title("Lunch")
                .amount(150.0)
                .category("Food")
                .expenseDate(LocalDate.now())
                .build();

        when(repository.findByExpenseIdAndUserUsername(
                1L,
                "testuser"))
                .thenReturn(Optional.of(expense));
        when(repository.save(any(Expense.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var response = expenseService.updateExpenseCategory(
                1L,
                "Travel");

        assertEquals("Lunch", response.getTitle());
        assertEquals(150.0, response.getAmount());
        assertEquals("Travel", response.getCategory());
        verify(repository).save(expense);
    }
}
