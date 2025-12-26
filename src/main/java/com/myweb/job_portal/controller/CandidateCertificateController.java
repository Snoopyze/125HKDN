package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.CandidateCertificateRequest;
import com.myweb.job_portal.dto.response.CandidateCertificateResponse;
import com.myweb.job_portal.service.CandidateCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/candidate-certificates")
@RequiredArgsConstructor
public class CandidateCertificateController {

    private final CandidateCertificateService candidateCertificateService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<CandidateCertificateResponse>> getAll() {
        return ResponseEntity.ok(candidateCertificateService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateCertificateResponse> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                candidateCertificateService.getById(id)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CandidateCertificateResponse>> getByUser(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                candidateCertificateService.getByUser(userId)
        );
    }

    @GetMapping("/job-application/{jobApplicationId}")
    public ResponseEntity<List<CandidateCertificateResponse>>
    getByJobApplication(
            @PathVariable Long jobApplicationId
    ) {
        return ResponseEntity.ok(candidateCertificateService.getByJobApplication(jobApplicationId)
        );
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<CandidateCertificateResponse> create(
            @RequestPart("data") String data,
            @RequestPart("certificate") MultipartFile certificate
    ) {
        CandidateCertificateRequest request =
                objectMapper.readValue(data, CandidateCertificateRequest.class);

        return ResponseEntity.ok(
                candidateCertificateService.create(request, certificate)
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<CandidateCertificateResponse> update(
            @PathVariable Long id,
            @RequestPart("certificate") MultipartFile certificate
    ) {
        return ResponseEntity.ok(
                candidateCertificateService.update(id, certificate)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        candidateCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
