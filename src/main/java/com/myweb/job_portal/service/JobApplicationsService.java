package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.request.JobApplicationRequest;
import com.myweb.job_portal.dto.request.UpdateApplicationStatusRequest;
import com.myweb.job_portal.dto.response.JobApplicationResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface JobApplicationsService {

    JobApplicationResponse apply(JobApplicationRequest request, MultipartFile cv);

    List<JobApplicationResponse> getAll();

    JobApplicationResponse getById(Long id);

    List<JobApplicationResponse> getByUser(Long userId);

    List<JobApplicationResponse> getByJobPost(Long jobPostId);

    JobApplicationResponse updateStatus(Long id, UpdateApplicationStatusRequest request);

    void delete(Long id);
}
