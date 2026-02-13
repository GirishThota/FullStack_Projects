package com.talenttrack.auth.dto;

import com.talenttrack.auth.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
  @Email @NotBlank String email,
  @NotBlank String name,
  @NotBlank String password,
  @NotNull Role role
) {}
