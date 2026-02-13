package com.talenttrack.auth;

import com.talenttrack.auth.dto.*;
import com.talenttrack.security.JwtService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public LoginResponse login(LoginRequest req) {
    User user = userRepository.findByEmail(req.email())
        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    if (!user.isEnabled()) {
      throw new IllegalArgumentException("Account disabled");
    }
    if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
      throw new IllegalArgumentException("Invalid email or password");
    }
    String token = jwtService.generateToken(user.getEmail(), Map.of(
        "role", user.getRole().name(),
        "name", user.getName()
    ));
    return new LoginResponse(token, user.getEmail(), user.getName(), user.getRole().name());
  }

  public User register(RegisterRequest req) {
    if (userRepository.existsByEmail(req.email())) {
      throw new IllegalArgumentException("Email already registered");
    }
    User user = User.builder()
        .email(req.email().toLowerCase().trim())
        .name(req.name().trim())
        .passwordHash(passwordEncoder.encode(req.password()))
        .role(req.role())
        .enabled(true)
        .build();
    return userRepository.save(user);
  }
}
