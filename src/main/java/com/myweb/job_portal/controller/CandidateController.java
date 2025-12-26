package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.UpdateProfileRequest;
import com.myweb.job_portal.dto.response.CandidateDetailResponse;
import com.myweb.job_portal.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getCandidateProfile(@PathVariable Long userId) {
        try {
            CandidateDetailResponse response = candidateService.getCandidateDetail(userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<?> updateCandidateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateProfileRequest request) {
        try {
            CandidateDetailResponse updateData = candidateService.updateCandidateDetail(userId, request);
            return ResponseEntity.ok(updateData);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
