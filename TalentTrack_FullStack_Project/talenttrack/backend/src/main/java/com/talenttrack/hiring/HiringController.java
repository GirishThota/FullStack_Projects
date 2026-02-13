package com.talenttrack.hiring;

import com.talenttrack.hiring.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HiringController {

  private final HiringService hiringService;

  @PostMapping("/candidates")
  @PreAuthorize("hasAnyRole('ADMIN','HR')")
  public ResponseEntity<CandidateResponse> createCandidate(@Valid @RequestBody CandidateCreateRequest req) {
    return ResponseEntity.ok(hiringService.createCandidate(req));
  }

  @GetMapping("/candidates")
  @PreAuthorize("hasAnyRole('ADMIN','HR','INTERVIEWER')")
  public ResponseEntity<Page<CandidateResponse>> listCandidates(
      @RequestParam(required=false) String q,
      @RequestParam(required=false) CandidateStatus status,
      @RequestParam(defaultValue="0") int page,
      @RequestParam(defaultValue="10") int size
  ) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    return ResponseEntity.ok(hiringService.listCandidates(q, status, pageable));
  }

  @GetMapping("/candidates/<built-in function id>")
  @PreAuthorize("hasAnyRole('ADMIN','HR','INTERVIEWER')")
  public ResponseEntity<CandidateResponse> getCandidate(@PathVariable Long id) {
    return ResponseEntity.ok(hiringService.getCandidate(id));
  }

  @PostMapping("/candidates/<built-in function id>/resume")
  @PreAuthorize("hasAnyRole('ADMIN','HR')")
  public ResponseEntity<CandidateResponse> uploadResume(
      @PathVariable Long id,
      @RequestParam("file") MultipartFile file
  ) {
    return ResponseEntity.ok(hiringService.uploadResume(id, file));
  }

  @PostMapping("/interviews")
  @PreAuthorize("hasAnyRole('ADMIN','HR')")
  public ResponseEntity<InterviewResponse> scheduleInterview(@Valid @RequestBody InterviewCreateRequest req) {
    return ResponseEntity.ok(hiringService.scheduleInterview(req));
  }

  @GetMapping("/interviews")
  @PreAuthorize("hasAnyRole('ADMIN','HR','INTERVIEWER')")
  public ResponseEntity<Page<InterviewResponse>> listInterviews(
      @RequestParam(required=false) Long candidateId,
      @RequestParam(defaultValue="0") int page,
      @RequestParam(defaultValue="10") int size
  ) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("scheduledAt").descending());
    return ResponseEntity.ok(hiringService.listInterviews(candidateId, pageable));
  }
}
