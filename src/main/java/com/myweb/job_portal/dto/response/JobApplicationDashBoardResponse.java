package com.myweb.job_portal.dto.response;

import lombok.*;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JobApplicationDashBoardResponse {

    private Long applicationId;
    private LocalDateTime appliedAt;
    private String status;

    private String candidateFullName;

    private String action;
}
