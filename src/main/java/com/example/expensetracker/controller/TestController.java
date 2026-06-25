package com.example.expensetracker.controller;

import com.example.expensetracker.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final JwtService jwtService;

    @GetMapping("/token")
    public String token() {

        return jwtService.generateToken(
                "john",
                "ROLE_USER"
        );
    }

    @GetMapping("/username")
    public String username() {

        String token =
                jwtService.generateToken(
                        "john",
                        "ROLE_USER"
                );

        return jwtService.extractUsername(token);
    }

    @GetMapping("/validate")
    public String validate() {

        String token =
                jwtService.generateToken(
                        "john",
                        "ROLE_USER"
                );

        boolean valid =
                jwtService.validateToken(
                        token,
                        "john"
                );

        return String.valueOf(valid);
    }
}