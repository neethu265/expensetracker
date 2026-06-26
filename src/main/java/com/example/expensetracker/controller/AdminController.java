package com.example.expensetracker.controller;

import com.example.expensetracker.dto.AdminUserCreateDTO;
import com.example.expensetracker.dto.AdminUserUpdateDTO;
import com.example.expensetracker.dto.UserResponseDTO;
import com.example.expensetracker.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminUserService adminUserService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Welcome Admin";
    }

    @GetMapping("/users")
    public List<UserResponseDTO> getAllUsers() {

        return adminUserService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserResponseDTO getUserById(
            @PathVariable Long id) {

        return adminUserService.getUserById(id);
    }

    @PostMapping("/users")
    public UserResponseDTO createUser(
            @Valid @RequestBody AdminUserCreateDTO dto) {

        return adminUserService.createUser(dto);
    }

    @PutMapping("/users/{id}")
    public UserResponseDTO updateUser(
            @PathVariable Long id,
            @Valid @RequestBody AdminUserUpdateDTO dto) {

        return adminUserService.updateUser(id, dto);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(
            @PathVariable Long id) {

        adminUserService.deleteUser(id);

        return "User Deleted Successfully";
    }
}
