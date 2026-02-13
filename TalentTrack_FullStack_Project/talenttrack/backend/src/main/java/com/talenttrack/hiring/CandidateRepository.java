package com.talenttrack.hiring;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
  Page<Candidate> findByStatus(CandidateStatus status, Pageable pageable);
  Page<Candidate> findByFullNameContainingIgnoreCase(String q, Pageable pageable);
}
