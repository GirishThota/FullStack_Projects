package com.talenttrack.hiring;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Interview {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false)
  private Candidate candidate;

  @Column(nullable=false, length=120)
  private String interviewerEmail;

  @Column(nullable=false)
  private Instant scheduledAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false, length=30)
  private InterviewStatus status;

  @Column(length=2000)
  private String notes;

  @PrePersist
  void prePersist() {
    if (status == null) status = InterviewStatus.SCHEDULED;
  }
}
