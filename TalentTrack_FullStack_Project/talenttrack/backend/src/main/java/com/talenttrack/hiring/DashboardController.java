package com.talenttrack.hiring;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

  private final CandidateRepository candidateRepository;

  @GetMapping("/stats")
  @PreAuthorize("hasAnyRole('ADMIN','HR','INTERVIEWER')")
  public Map<String, Object> stats() {
    long total = candidateRepository.count();
    long applied = candidateRepository.findByStatus(CandidateStatus.APPLIED, org.springframework.data.domain.Pageable.unpaged()).getTotalElements();
    long shortlisted = candidateRepository.findByStatus(CandidateStatus.SHORTLISTED, org.springframework.data.domain.Pageable.unpaged()).getTotalElements();
    long scheduled = candidateRepository.findByStatus(CandidateStatus.INTERVIEW_SCHEDULED, org.springframework.data.domain.Pageable.unpaged()).getTotalElements();
    long selected = candidateRepository.findByStatus(CandidateStatus.SELECTED, org.springframework.data.domain.Pageable.unpaged()).getTotalElements();
    long rejected = candidateRepository.findByStatus(CandidateStatus.REJECTED, org.springframework.data.domain.Pageable.unpaged()).getTotalElements();

    return Map.of(
        "totalCandidates", total,
        "applied", applied,
        "shortlisted", shortlisted,
        "interviewScheduled", scheduled,
        "selected", selected,
        "rejected", rejected
    );
  }
}
