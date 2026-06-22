package com.example.expensetracker.controller;

import com.example.expensetracker.dto.ExpenseRequestDTO;
import com.example.expensetracker.dto.ExpenseResponseDTO;
import com.example.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService service;

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> addExpense(
            @Valid @RequestBody ExpenseRequestDTO dto) {

        return ResponseEntity.ok(
                service.addExpense(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getExpense(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getExpenseById(id));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses() {

        return ResponseEntity.ok(
                service.getAllExpenses());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequestDTO dto) {

        return ResponseEntity.ok(
                service.updateExpense(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(
            @PathVariable Long id) {

        service.deleteExpense(id);

        return ResponseEntity.ok("Expense Deleted Successfully");
    }
}