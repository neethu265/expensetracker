package com.example.expensetracker.repository;

import com.example.expensetracker.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserUsername(String username);

    List<Expense> findByCategoryAndUserUsername(
            String category,
            String username);

    Optional<Expense> findByExpenseIdAndUserUsername(
            Long expenseId,
            String username);

    Page<Expense> findByUserUsername(
            String username,
            Pageable pageable);
}
