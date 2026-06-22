package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseRequestDTO;
import com.example.expensetracker.dto.ExpenseResponseDTO;
import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.exception.ExpenseNotFoundException;
import com.example.expensetracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository repository;

    @Override
    public ExpenseResponseDTO addExpense(ExpenseRequestDTO dto) {

        Expense expense = Expense.builder()
                .title(dto.getTitle())
                .amount(dto.getAmount())
                .category(dto.getCategory())
                .expenseDate(LocalDate.now())
                .build();

        Expense saved = repository.save(expense);

        return mapToResponse(saved);
    }

    @Override
    @Cacheable(value = "expenses", key = "#id")
    public ExpenseResponseDTO getExpenseById(Long id) {

        Expense expense = repository.findById(id)
                .orElseThrow(() ->
                        new ExpenseNotFoundException("Expense not found"));

        return mapToResponse(expense);
    }

    @Override
    public List<ExpenseResponseDTO> getAllExpenses() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @CachePut(value = "expenses", key = "#id")
    public ExpenseResponseDTO updateExpense(Long id,
                                            ExpenseRequestDTO dto) {

        Expense expense = repository.findById(id)
                .orElseThrow(() ->
                        new ExpenseNotFoundException("Expense not found"));

        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());
        expense.setCategory(dto.getCategory());

        Expense updated = repository.save(expense);

        return mapToResponse(updated);
    }

    @Override
    @CacheEvict(value = "expenses", key = "#id")
    public void deleteExpense(Long id) {

        repository.deleteById(id);
    }

    private ExpenseResponseDTO mapToResponse(Expense expense) {

        return ExpenseResponseDTO.builder()
                .expenseId(expense.getExpenseId())
                .title(expense.getTitle())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .expenseDate(expense.getExpenseDate())
                .build();
    }
}