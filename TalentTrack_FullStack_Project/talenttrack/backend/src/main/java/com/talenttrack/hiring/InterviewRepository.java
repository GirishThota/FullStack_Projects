package com.talenttrack.hiring;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
  Page<Interview> findByCandidateId(Long candidateId, Pageable pageable);
}
