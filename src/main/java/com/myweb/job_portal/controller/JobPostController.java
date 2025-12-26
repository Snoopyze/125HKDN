package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.response.JobDetailResponse;
import com.myweb.job_portal.dto.response.JobListItemResponse;
import com.myweb.job_portal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-post")
public class JobPostController {

    @Autowired
    JobService jobService;

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getJobDetail(@PathVariable Long id){
        try {
            JobDetailResponse response= jobService.getJobDetail(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list")
    public  ResponseEntity<List<JobListItemResponse>> getAllJobs(){
        return ResponseEntity.ok(jobService.getAlljobs());
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobListItemResponse>> searchJobs(
            @RequestParam(required = false) Long posId,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) Long companyId
    ){
        return ResponseEntity.ok(jobService.searchJobPosts(posId,province,companyId));
    }
}
