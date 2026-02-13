package com.talenttrack.hiring.dto;

import com.talenttrack.hiring.CandidateStatus;
import java.time.Instant;

public record CandidateResponse(
  Long id,
  String fullName,
  String email,
  String phone,
  CandidateStatus status,
  Integer experienceMonths,
  String extractedSkills,
  Instant createdAt
) {}
