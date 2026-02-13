package com.talenttrack.hiring;

import com.talenttrack.hiring.dto.*;
import java.io.InputStream;
import java.nio.file.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class HiringService {

  private final CandidateRepository candidateRepository;
  private final InterviewRepository interviewRepository;
  private final ResumeParser resumeParser;

  @Value("${app.storage.resumeDir}")
  private String resumeDir;

  public CandidateResponse createCandidate(CandidateCreateRequest req) {
    Candidate c = Candidate.builder()
        .fullName(req.fullName().trim())
        .email(req.email().toLowerCase().trim())
        .phone(req.phone())
        .experienceMonths(req.experienceMonths())
        .status(CandidateStatus.APPLIED)
        .build();
    c = candidateRepository.save(c);
    return toResponse(c);
  }

  public Page<CandidateResponse> listCandidates(String q, CandidateStatus status, Pageable pageable) {
    Page<Candidate> page;
    if (q != null && !q.isBlank()) {
      page = candidateRepository.findByFullNameContainingIgnoreCase(q.trim(), pageable);
    } else if (status != null) {
      page = candidateRepository.findByStatus(status, pageable);
    } else {
      page = candidateRepository.findAll(pageable);
    }
    return page.map(this::toResponse);
  }

  public CandidateResponse getCandidate(Long id) {
    return toResponse(candidateRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Candidate not found")));
  }

  public CandidateResponse uploadResume(Long candidateId, MultipartFile file) {
    Candidate c = candidateRepository.findById(candidateId)
        .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));
    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("File is empty");
    }
    try {
      Files.createDirectories(Paths.get(resumeDir));
      String safeName = "candidate_" + candidateId + "_" + System.currentTimeMillis() + ".pdf";
      Path out = Paths.get(resumeDir).resolve(safeName).normalize();
      try (InputStream in = file.getInputStream()) {
        Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
      }
      // Extract skills (best-effort)
      try (InputStream in2 = Files.newInputStream(out)) {
        String skills = resumeParser.extractSkillsFromPdf(in2);
        c.setExtractedSkills(skills);
      }
      c.setResumePath(out.toString());
      c = candidateRepository.save(c);
      return toResponse(c);
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to upload resume");
    }
  }

  public InterviewResponse scheduleInterview(InterviewCreateRequest req) {
    Candidate c = candidateRepository.findById(req.candidateId())
        .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));
    Interview i = Interview.builder()
        .candidate(c)
        .interviewerEmail(req.interviewerEmail().toLowerCase().trim())
        .scheduledAt(req.scheduledAt())
        .status(InterviewStatus.SCHEDULED)
        .build();
    i = interviewRepository.save(i);

    // update candidate status
    c.setStatus(CandidateStatus.INTERVIEW_SCHEDULED);
    candidateRepository.save(c);

    return toResponse(i);
  }

  public Page<InterviewResponse> listInterviews(Long candidateId, Pageable pageable) {
    Page<Interview> page = (candidateId == null)
        ? interviewRepository.findAll(pageable)
        : interviewRepository.findByCandidateId(candidateId, pageable);
    return page.map(this::toResponse);
  }

  private CandidateResponse toResponse(Candidate c) {
    return new CandidateResponse(
        c.getId(),
        c.getFullName(),
        c.getEmail(),
        c.getPhone(),
        c.getStatus(),
        c.getExperienceMonths(),
        c.getExtractedSkills(),
        c.getCreatedAt()
    );
  }

  private InterviewResponse toResponse(Interview i) {
    return new InterviewResponse(
        i.getId(),
        i.getCandidate().getId(),
        i.getCandidate().getFullName(),
        i.getInterviewerEmail(),
        i.getScheduledAt(),
        i.getStatus(),
        i.getNotes()
    );
  }
}
