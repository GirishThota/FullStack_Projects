package com.talenttrack.hiring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CandidateCreateRequest(
  @NotBlank String fullName,
  @Email @NotBlank String email,
  String phone,
  Integer experienceMonths
) {}
