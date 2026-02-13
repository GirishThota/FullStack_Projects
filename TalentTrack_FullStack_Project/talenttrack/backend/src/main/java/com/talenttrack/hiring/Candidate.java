package com.talenttrack.hiring;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import java.time.Instant;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Candidate {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, length=120)
  private String fullName;

  @Email
  @Column(nullable=false, unique=true, length=160)
  private String email;

  @Column(length=40)
  private String phone;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false, length=30)
  private CandidateStatus status;

  private Integer experienceMonths;

  @Column(length=255)
  private String resumePath;

  @Column(length=2000)
  private String extractedSkills; // comma-separated simple extraction

  @Column(nullable=false, updatable=false)
  private Instant createdAt;

  @PrePersist
  void prePersist() {
    if (createdAt == null) createdAt = Instant.now();
    if (status == null) status = CandidateStatus.APPLIED;
  }
}
