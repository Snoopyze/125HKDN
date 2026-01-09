package com.myweb.job_portal.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.myweb.job_portal.dto.request.ApplyJobRequest;
import com.myweb.job_portal.dto.response.ApplicationHistoryResponse;
import com.myweb.job_portal.dto.response.JobApplicationDashBoardResponse;
import com.myweb.job_portal.entity.*;
import com.myweb.job_portal.enums.ApplicationStatus;
import com.myweb.job_portal.repository.ConversationRepository;
import com.myweb.job_portal.repository.JobApplicationRepository;
import com.myweb.job_portal.repository.JobPostRepository;
import com.myweb.job_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Transactional
    public JobApplications applyJob(ApplyJobRequest request) {

        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(()-> new RuntimeException("Người dùng không tồn tại"));

        JobPosts jobPosts = jobPostRepository.findById(request.getJobId())
                .orElseThrow(()-> new RuntimeException("Công việc không tồn tại"));

        boolean isApplied =  jobApplicationRepository. existsByUsers_IdAndJobPosts_Id(user.getId(), jobPosts.getId());
        if(isApplied){
            throw new RuntimeException("Bạn đã nộp đơn vào vị trí này rồi!");
        }

        String originalFilename = request.getCvFile().getOriginalFilename();
        String cvUrl;
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    request.getCvFile().getBytes(),
                    ObjectUtils.asMap(  "resource_type", "raw",
                                                "folder", "job_applications/cv",
                                                "public_id", originalFilename)
            );
            cvUrl = uploadResult.get("secure_url").toString();
        }catch (IOException e){
            throw new RuntimeException("Upload fail");
        }

        JobApplications jobApplications = new JobApplications();
        jobApplications.setJobPosts(jobPosts);
        jobApplications.setUsers(user);
        jobApplications.setCvUrl(cvUrl);
        jobApplications.setStatus(ApplicationStatus.pending);
        jobApplications.setAppliedAt(LocalDateTime.now());

        JobApplications save = jobApplicationRepository.save(jobApplications);

        Conversation conversation = new Conversation();
        conversation.setJobApplications(save);

        conversationRepository.save(conversation);
        return save;
    }

    public List<ApplicationHistoryResponse> getApplicationHistory(Long userId, String statusStr) {

        ApplicationStatus status = null;
        if(statusStr != null && !statusStr.trim().isEmpty()){
            try {
                status = ApplicationStatus.valueOf(statusStr.trim().toLowerCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Trạng thái không hợp lệ: " + statusStr);
            }
        }

        List<JobApplications> jobApplications = jobApplicationRepository.findHistory(userId, status);

        return jobApplications.stream().map(app -> {
            Companies companies = app.getJobPosts().getCompanyProjects().getCompany();
            Positions positions = app.getJobPosts().getPositions();

            return ApplicationHistoryResponse.builder()
                    .applicationId(app.getId())
                    .status(app.getStatus().name())
                    .jobPostId(app.getJobPosts().getId())
                    .jobPostName(app.getJobPosts().getTittle())
                    .jobPostDescription(app.getJobPosts().getJobDescription())
                    .applicationDeadline(app.getJobPosts().getApplicationDeadline())
                    .positionId(app.getJobPosts().getPositions().getId())
                    .positionName(app.getJobPosts().getPositions().getName())
                    .projectId(app.getJobPosts().getCompanyProjects().getId())
                    .projectName(app.getJobPosts().getCompanyProjects().getTittle())
                    .companyId(app.getJobPosts().getCompanyProjects().getCompany().getId())
                    .legalName(app.getJobPosts().getCompanyProjects().getCompany().getLegalName())
                    .provinceAddress(app.getJobPosts().getCompanyProjects().getCompany().getProvinnceAddress())
                    .logoUrl(app.getJobPosts().getCompanyProjects().getCompany().getLogoUrl())
                    .build();
        }).collect(Collectors.toList());

    }

    public List<JobApplicationDashBoardResponse> getAllApplications() {
        List<JobApplications> jobApplications = jobApplicationRepository.findAllJobApplications();

        return jobApplications.stream().map(app -> {
            Companies companies = app.getJobPosts().getCompanyProjects().getCompany();
            CandidateProfiles candidateProfiles = app.getUsers().getCandidateProfile();

            return JobApplicationDashBoardResponse.builder()
                    .applicationId(app.getId())
                    .status(app.getStatus().name())
                    .appliedAt(LocalDateTime.now())
                    .candidateFullName(app.getUsers().getCandidateProfile().getFullName())
                    .action("Đã nộp CV vào Công Ty " + app.getJobPosts().getCompanyProjects().getCompany().getShortName())
                    .build();


        }).collect(Collectors.toList());
    }

    public boolean hasApplied(Long userId, Long applicationId) {
        return jobApplicationRepository.existsByUsers_IdAndJobPosts_Id(userId, applicationId);
    }
}
