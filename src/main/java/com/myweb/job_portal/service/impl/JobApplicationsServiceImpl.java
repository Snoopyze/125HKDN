package com.myweb.job_portal.service.impl;

import com.myweb.job_portal.dto.request.JobApplicationRequest;
import com.myweb.job_portal.dto.request.UpdateApplicationStatusRequest;
import com.myweb.job_portal.dto.response.JobApplicationResponse;
import com.myweb.job_portal.entity.JobApplications;
import com.myweb.job_portal.entity.JobPosts;
import com.myweb.job_portal.entity.Users;
import com.myweb.job_portal.repository.JobApplicationRepository;
import com.myweb.job_portal.repository.JobPostRepository;
import com.myweb.job_portal.repository.UserRepository;
import com.myweb.job_portal.service.CloudinaryService;
import com.myweb.job_portal.service.JobApplicationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationsServiceImpl implements JobApplicationsService {

    private final JobApplicationRepository repository;
    private final JobPostRepository jobPostsRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public JobApplicationResponse apply(
            JobApplicationRequest request,
            MultipartFile cv
    ) {

        JobPosts jobPost = jobPostsRepository.findById(request.getJobPostId())
                .orElseThrow(() -> new RuntimeException("Job post not found"));

        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobApplications application = JobApplications.builder()
                .jobPosts(jobPost)
                .users(user)
                .appliedAt(LocalDateTime.now())
                .build();

        if (cv != null && !cv.isEmpty()) {
            String cvUrl = cloudinaryService.uploadAndGetUrl(
                    cv,
                    "job_applications/cv"
            );
            application.setCvUrl(cvUrl);
        }

        return JobApplicationResponse.fromEntity(
                repository.save(application)
        );
    }

    @Override
    public List<JobApplicationResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(JobApplicationResponse::fromEntity)
                .toList();
    }

    @Override
    public JobApplicationResponse getById(Long id) {
        return repository.findById(id)
                .map(JobApplicationResponse::fromEntity)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    @Override
    public List<JobApplicationResponse> getByUser(Long userId) {
        return repository.findByUsers_Id(userId)
                .stream()
                .map(JobApplicationResponse::fromEntity)
                .toList();
    }

    @Override
    public List<JobApplicationResponse> getByJobPost(Long jobPostId) {
        return repository.findByJobPosts_Id(jobPostId)
                .stream()
                .map(JobApplicationResponse::fromEntity)
                .toList();
    }

    @Override
    public JobApplicationResponse updateStatus(
            Long id,
            UpdateApplicationStatusRequest request
    ) {
        JobApplications application = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(request.getStatus());

        return JobApplicationResponse.fromEntity(
                repository.save(application)
        );
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Application not found");
        }
        repository.deleteById(id);
    }
}
