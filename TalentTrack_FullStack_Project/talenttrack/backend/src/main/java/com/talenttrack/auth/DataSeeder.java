package com.talenttrack.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Bean
  CommandLineRunner seedAdmin() {
    return args -> {
      String adminEmail = "admin@talenttrack.dev";
      if (!userRepository.existsByEmail(adminEmail)) {
        userRepository.save(User.builder()
            .email(adminEmail)
            .name("Admin")
            .passwordHash(passwordEncoder.encode("admin123"))
            .role(Role.ADMIN)
            .enabled(true)
            .build());
      }
    };
  }
}
