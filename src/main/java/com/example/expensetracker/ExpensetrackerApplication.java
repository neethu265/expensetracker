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

            if (repository.findByUsername("neethu").isEmpty()) {

                repository.save(
                        User.builder()
                                .username("neethu")
                                .password(
                                        encoder.encode("neethu123"))
                                .build()
                );
            }
        };
    }
}
