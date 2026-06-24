package com.example.expensetracker.service;

import com.example.expensetracker.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);
}