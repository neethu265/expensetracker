package com.example.expensetracker.service;

import com.example.expensetracker.dto.AdminUserCreateDTO;
import com.example.expensetracker.dto.AdminUserUpdateDTO;
import com.example.expensetracker.dto.UserResponseDTO;
import com.example.expensetracker.entity.User;
import com.example.expensetracker.exception.UserNotFoundException;
import com.example.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {

        return mapToResponse(findUser(id));
    }

    public UserResponseDTO createUser(AdminUserCreateDTO dto) {

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(normalizeRole(dto.getRole()))
                .build();

        return mapToResponse(userRepository.save(user));
    }

    public UserResponseDTO updateUser(
            Long id,
            AdminUserUpdateDTO dto) {

        User user = findUser(id);

        userRepository.findByUsername(dto.getUsername())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Username already exists");
                });

        user.setUsername(dto.getUsername());
        user.setRole(normalizeRole(dto.getRole()));

        if (dto.getPassword() != null &&
                !dto.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(dto.getPassword()));
        }

        return mapToResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {

        User user = findUser(id);

        userRepository.delete(user);
    }

    private User findUser(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));
    }

    private UserResponseDTO mapToResponse(User user) {

        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    private String normalizeRole(String role) {

        String normalized = role.trim().toUpperCase();

        if (!normalized.startsWith("ROLE_")) {
            normalized = "ROLE_" + normalized;
        }

        if (!normalized.equals("ROLE_ADMIN") &&
                !normalized.equals("ROLE_USER")) {

            throw new IllegalArgumentException(
                    "Role must be ROLE_ADMIN or ROLE_USER");
        }

        return normalized;
    }
}
