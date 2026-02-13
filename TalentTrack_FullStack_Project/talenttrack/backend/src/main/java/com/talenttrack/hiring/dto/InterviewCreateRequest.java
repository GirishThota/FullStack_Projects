package com.talenttrack.hiring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record InterviewCreateRequest(
  @NotNull Long candidateId,
  @Email @NotNull String interviewerEmail,
  @NotNull Instant scheduledAt
) {}
