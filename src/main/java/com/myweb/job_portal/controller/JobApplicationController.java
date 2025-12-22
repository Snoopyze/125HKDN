package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.ApplyJobRequest;
import com.myweb.job_portal.dto.response.ApplicationHistoryResponse;
import com.myweb.job_portal.entity.JobApplications;
import com.myweb.job_portal.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    @Autowired
    JobApplicationService jobApplicationService;

    @PostMapping(   value = "/apply",
                    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> applyJob(@ModelAttribute @Valid ApplyJobRequest request) {
        try {
            JobApplications newApp = jobApplicationService.applyJob(request);
            return  ResponseEntity.ok("Nộp hồ sơ thành công! Mã hồ sơ: " + newApp.getId());
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<ApplicationHistoryResponse>> getApplicationHistory(
            @RequestParam Long userId,
            @RequestParam(required = false) String status
    ) {
        return  ResponseEntity.ok(jobApplicationService.getApplicationHistory(userId,status));
    }

}
