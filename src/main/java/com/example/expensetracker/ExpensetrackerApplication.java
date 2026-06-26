package com.example.expensetracker;

import com.example.expensetracker.entity.User;
import com.example.expensetracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableCaching
public class ExpensetrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpensetrackerApplication.class, args);
    }
    @Bean
    CommandLineRunner createUser(
            UserRepository repository,
            PasswordEncoder encoder) {

        return args -> {

            repository.findByUsername("neethu")
                    .ifPresentOrElse(user -> {
                        if (user.getRole() == null ||
                                user.getRole().isBlank()) {

                            user.setRole("ROLE_ADMIN");
                            repository.save(user);
                        }
                    }, () -> {

                repository.save(
                        User.builder()
                                .username("neethu")
                                .password(
                                        encoder.encode("neethu123"))
                                .role("ROLE_ADMIN")
                                .build()
                );
                    });
        };
    }
}
