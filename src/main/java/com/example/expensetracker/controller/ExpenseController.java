package com.example.expensetracker.controller;

import com.example.expensetracker.dto.ExpenseDashboardDTO;
import com.example.expensetracker.dto.ExpenseCategoryUpdateDTO;
import com.example.expensetracker.dto.ExpenseRequestDTO;
import com.example.expensetracker.dto.ExpenseResponseDTO;
import com.example.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @PatchMapping("/{id}/category")
    public ResponseEntity<ExpenseResponseDTO> updateExpenseCategory(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseCategoryUpdateDTO dto) {

        return ResponseEntity.ok(
                service.updateExpenseCategory(
                        id,
                        dto.getCategory()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(
            @PathVariable Long id) {

        service.deleteExpense(id);

        return ResponseEntity.ok("Expense Deleted Successfully");
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ExpenseResponseDTO>>
    getByCategory(@PathVariable String category){

        return ResponseEntity.ok(
                service.getExpensesByCategory(category));
    }
    @GetMapping("/dashboard")
    public ResponseEntity<ExpenseDashboardDTO>
    getDashboard(){

        return ResponseEntity.ok(
                service.getDashboard());
    }
    @GetMapping("/offset")
    public ResponseEntity<List<ExpenseResponseDTO>>
    getExpensesOffset(

            @RequestParam int offset,

            @RequestParam int limit) {

        return ResponseEntity.ok(
                service.getExpensesByOffset(
                        offset,
                        limit));
    }
}
