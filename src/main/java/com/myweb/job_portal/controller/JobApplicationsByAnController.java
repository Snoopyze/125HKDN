package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.JobApplicationRequest;
import com.myweb.job_portal.dto.request.UpdateApplicationStatusRequest;
import com.myweb.job_portal.dto.response.JobApplicationResponse;
import com.myweb.job_portal.service.JobApplicationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
@RequiredArgsConstructor
public class JobApplicationsByAnController {

    private final JobApplicationsService service;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public JobApplicationResponse apply(
            @RequestPart("data") String data, @RequestPart(value = "cv", required = false) MultipartFile cv
    ) {
        JobApplicationRequest request =
                objectMapper.readValue(
                        data, JobApplicationRequest.class
                );
        return service.apply(request, cv);
    }

    @GetMapping
    public List<JobApplicationResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public JobApplicationResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<JobApplicationResponse> getByUser(
            @PathVariable Long userId
    ) {
        return service.getByUser(userId);
    }

    @GetMapping("/job/{jobPostId}")
    public List<JobApplicationResponse> getByJobPost(
            @PathVariable Long jobPostId
    ) {
        return service.getByJobPost(jobPostId);
    }

    @PutMapping("/{id}/status")
    public JobApplicationResponse updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateApplicationStatusRequest request
    ) {
        return service.updateStatus(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
