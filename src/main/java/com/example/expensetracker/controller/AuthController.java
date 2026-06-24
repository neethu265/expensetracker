package com.example.expensetracker.controller;

import com.example.expensetracker.dto.RegisterRequest;
import com.example.expensetracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                authService.register(request));
    }
}