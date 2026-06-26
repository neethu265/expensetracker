package com.example.expensetracker.service;

import com.example.expensetracker.dto.AdminUserCreateDTO;
import com.example.expensetracker.dto.AdminUserUpdateDTO;
import com.example.expensetracker.entity.User;
import com.example.expensetracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminUserService adminUserService;

    @Test
    void shouldCreateUserWithEncodedPassword() {

        AdminUserCreateDTO request = new AdminUserCreateDTO();
        request.setUsername("admin2");
        request.setPassword("secret");
        request.setRole("ROLE_ADMIN");

        when(userRepository.findByUsername("admin2"))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode("secret"))
                .thenReturn("encoded-secret");
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    user.setId(1L);
                    return user;
                });

        var response = adminUserService.createUser(request);

        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        assertEquals(1L, response.getId());
        assertEquals("admin2", response.getUsername());
        assertEquals("ROLE_ADMIN", response.getRole());
        verify(userRepository).save(userCaptor.capture());
        assertEquals("encoded-secret", userCaptor.getValue().getPassword());
    }

    @Test
    void shouldUpdateUserRoleAndPassword() {

        User user = User.builder()
                .id(1L)
                .username("user1")
                .password("old")
                .role("ROLE_USER")
                .build();

        AdminUserUpdateDTO request = new AdminUserUpdateDTO();
        request.setUsername("user1");
        request.setPassword("new-password");
        request.setRole("ROLE_ADMIN");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        when(userRepository.findByUsername("user1"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.encode("new-password"))
                .thenReturn("encoded-new");
        when(userRepository.save(user))
                .thenReturn(user);

        var response = adminUserService.updateUser(1L, request);

        assertEquals("ROLE_ADMIN", response.getRole());
        assertEquals("encoded-new", user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void shouldRejectDuplicateUsernameOnCreate() {

        AdminUserCreateDTO request = new AdminUserCreateDTO();
        request.setUsername("existing");
        request.setPassword("secret");
        request.setRole("ROLE_USER");

        when(userRepository.findByUsername("existing"))
                .thenReturn(Optional.of(User.builder().build()));

        assertThrows(
                IllegalArgumentException.class,
                () -> adminUserService.createUser(request));
    }
}
