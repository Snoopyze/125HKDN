package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.JobPostRequest;
import com.myweb.job_portal.dto.response.JobPostResponse;
import com.myweb.job_portal.service.JobPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-posts")
public class JobPostByAnController {

    private final JobPostService service;

    public JobPostByAnController(JobPostService service) {
        this.service = service;
    }

    @GetMapping
    public List<JobPostResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public JobPostResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<JobPostResponse> create(
            @RequestBody @Validated JobPostRequest request) {
        return ResponseEntity.status(201).body(service.create(request));
    }

    @PutMapping("/{id}")
    public JobPostResponse update(
            @PathVariable Long id,
            @RequestBody @Validated JobPostRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/company/{companyId}")
    public List<JobPostResponse> getJobPostsByCompany(@PathVariable Long companyId) {
        return service.getAllJobPostsByCompany(companyId);
    }
}
