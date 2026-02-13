package com.talenttrack.hiring.dto;

import com.talenttrack.hiring.InterviewStatus;
import java.time.Instant;

public record InterviewResponse(
  Long id,
  Long candidateId,
  String candidateName,
  String interviewerEmail,
  Instant scheduledAt,
  InterviewStatus status,
  String notes
) {}
