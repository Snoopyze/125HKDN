package com.myweb.job_portal.service.impl;

import com.myweb.job_portal.dto.request.JobPostRequest;
import com.myweb.job_portal.dto.response.JobPostResponse;
import com.myweb.job_portal.entity.CompanyProjects;
import com.myweb.job_portal.entity.JobPosts;
import com.myweb.job_portal.entity.Positions;
import com.myweb.job_portal.repository.CompanyProjectRepository;
import com.myweb.job_portal.repository.JobPostRepository;
import com.myweb.job_portal.repository.PositionRepository;
import com.myweb.job_portal.service.JobPostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPostServiceImpl implements JobPostService {

    private final JobPostRepository jobPostRepository;
    private final CompanyProjectRepository projectRepository;
    private final PositionRepository positionsRepository;

    public JobPostServiceImpl(
            JobPostRepository jobPostRepository,
            CompanyProjectRepository projectRepository,
            PositionRepository positionsRepository) {
        this.jobPostRepository = jobPostRepository;
        this.projectRepository = projectRepository;
        this.positionsRepository = positionsRepository;
    }

    @Override
    public List<JobPostResponse> getAll() {
        return jobPostRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public JobPostResponse getById(Long id) {
        JobPosts jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobPost not found"));
        return mapToResponse(jobPost);
    }

    @Override
    public JobPostResponse create(JobPostRequest request) {

        CompanyProjects project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Positions position = positionsRepository.findById(request.getPositionId())
                .orElseThrow(() -> new EntityNotFoundException("Position not found"));

        JobPosts jobPost = JobPosts.builder()
                .companyProjects(project)
                .positions(position)
                .tittle(request.getTittle())
                .applicationDeadline(request.getApplicationDeadline())
                .quantity(request.getQuantity())
                .jobDescription(request.getJobDescription())
                .build();

        return mapToResponse(jobPostRepository.save(jobPost));
    }

    @Override
    public JobPostResponse update(Long id, JobPostRequest request) {

        JobPosts jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobPost not found"));

        CompanyProjects project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Positions position = positionsRepository.findById(request.getPositionId())
                .orElseThrow(() -> new EntityNotFoundException("Position not found"));

        jobPost.setCompanyProjects(project);
        jobPost.setPositions(position);
        jobPost.setTittle(request.getTittle());
        jobPost.setApplicationDeadline(request.getApplicationDeadline());
        jobPost.setQuantity(request.getQuantity());
        jobPost.setJobDescription(request.getJobDescription());

        return mapToResponse(jobPostRepository.save(jobPost));
    }

    @Override
    public void delete(Long id) {
        JobPosts jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobPost not found"));
        jobPostRepository.delete(jobPost);
    }

    private JobPostResponse mapToResponse(JobPosts jobPost) {
        return new JobPostResponse(
                jobPost.getId(),
                jobPost.getTittle(),
                jobPost.getApplicationDeadline(),
                jobPost.getQuantity(),
                jobPost.getJobDescription(),
                jobPost.getCompanyProjects().getId(),
                jobPost.getPositions().getId()
        );
    }
    public List<JobPostResponse> getAllJobPostsByCompany(Long companyId) {

        return jobPostRepository
                .findByCompanyProjects_Company_Id(companyId)
                .stream()
                .map(job -> new JobPostResponse(
                        job.getId(),
                        job.getTittle(),
                        job.getApplicationDeadline(),
                        job.getQuantity(),
                        job.getJobDescription(),
                        job.getCompanyProjects().getId(),
                        job.getPositions().getId()
                ))
                .toList();
    }
}
