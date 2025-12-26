package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.request.JobPostRequest;
import com.myweb.job_portal.dto.response.JobPostResponse;

import java.util.List;

public interface JobPostService {

    List<JobPostResponse> getAll();

    JobPostResponse getById(Long id);

    JobPostResponse create(JobPostRequest request);

    JobPostResponse update(Long id, JobPostRequest request);

    void delete(Long id);

    List<JobPostResponse> getAllJobPostsByCompany(Long companyId);
}
