package com.example.expensetracker.service.impl;

import com.example.expensetracker.dto.RegisterRequest;
import com.example.expensetracker.entity.User;
import com.example.expensetracker.repository.UserRepository;
import com.example.expensetracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(RegisterRequest request) {

        if (userRepository
                .findByUsername(request.getUsername())
                .isPresent()) {

            return "Username already exists";
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()))
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }
}