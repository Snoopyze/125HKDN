package com.myweb.job_portal.dto.response;

import com.myweb.job_portal.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class JobApplicationResponse {

    private Long id;
    private Long jobPostId;
    private Long userId;
    private String cvUrl;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;

    public static JobApplicationResponse fromEntity(
            com.myweb.job_portal.entity.JobApplications entity
    ) {
        return new JobApplicationResponse(
                entity.getId(),
                entity.getJobPosts().getId(),
                entity.getUsers().getId(),
                entity.getCvUrl(),
                entity.getStatus(),
                entity.getAppliedAt()
        );
    }
}
