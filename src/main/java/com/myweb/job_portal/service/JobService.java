package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.response.JobDetailResponse;
import com.myweb.job_portal.dto.response.JobListItemResponse;
import com.myweb.job_portal.entity.*;
import com.myweb.job_portal.repository.JobPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobPostRepository jobPostRepository;

    public JobDetailResponse getJobDetail(Long id) {

        JobPosts jobPosts = jobPostRepository.findById(id).orElseThrow(()->new RuntimeException("Không tìm thấy bài đăng"));

        Positions positions = jobPosts.getPositions();
        CompanyProjects companyProjects = jobPosts.getCompanyProjects();
        Companies companies = companyProjects.getCompany();
        Users owner = companies.getOwner();

        String address = companies.getStreetAddress() + ", "
                + companies.getWardAddress() + ", "
                + companies.getProvinnceAddress();

        JobDetailResponse.JobDetailResponseBuilder responseBuilder = JobDetailResponse.builder()
                //company info
                .companyId(companies.getId())
                .userId(owner.getId())
                .ownerName(companies.getOwnerName())
                .legalName(companies.getOwnerName())
                .shortName(companies.getShortName())
                .streetAddress(companies.getStreetAddress())
                .wardAddress(companies.getWardAddress())
                .provinceAddress(companies.getProvinnceAddress())
                .fullAddress(address)
                .websiteUrl(companies.getWebsiteUrl())
                .industry(companies.getIndustry())
                .companySize(companies.getCompanySize())
                .description(companies.getDescription())
                .logoUrl(companies.getLogoUrl())
                //project info
                .projectId(companyProjects.getId())
                .projectTitle(companyProjects.getTittle())
                //position info
                .positionId(positions.getId())
                .positionName(positions.getName())
                //job post info
                .jobId(jobPosts.getId())
                .jobTitle(jobPosts.getTittle())
                .jobDescription(jobPosts.getJobDescription())
                .jobQuantity(jobPosts.getQuantity())
                .applicationDeadline(jobPosts.getApplicationDeadline());

        return responseBuilder.build();
    }

    public List<JobListItemResponse> getAlljobs() {
        List<JobPosts> jobPosts = jobPostRepository.findAllJobsWithDetails();

        return jobPosts.stream().map(job -> {
            Companies companies = job.getCompanyProjects().getCompany();

            return JobListItemResponse.builder()
                    .jobId(job.getId())
                    .jobTitle(job.getTittle())
                    .jobDescription(job.getJobDescription())
                    .projectId(job.getCompanyProjects().getId())
                    .projectTitle(job.getCompanyProjects().getTittle())
                    .companyId(companies.getId())
                    .legalName(companies.getLegalName())
                    .provinceAddress(companies.getProvinnceAddress())
                    .logoUrl(companies.getLogoUrl())
                    .build();
        }).collect(Collectors.toList());
    }

    public List<JobListItemResponse> searchJobPosts(Long positionId, String province, Long companyId) {
        List<JobPosts> jobs = jobPostRepository.searchJobPosts(positionId, province, companyId);

        return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private JobListItemResponse convertToDTO(JobPosts job) {
        Companies companies = job.getCompanyProjects().getCompany();
        Positions positions = job.getPositions();

        return JobListItemResponse.builder()
                .jobId(job.getId())
                .jobTitle(job.getTittle())
                .jobDescription(job.getJobDescription())
                .projectId(job.getCompanyProjects().getId())
                .projectTitle(job.getCompanyProjects().getTittle())
                .companyId(companies.getId())
                .legalName(companies.getLegalName())
                .provinceAddress(companies.getProvinnceAddress())
                .logoUrl(companies.getLogoUrl())
                .posId(positions.getId())
                .posName(positions.getName())
                .build();
    }


}
