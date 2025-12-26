package com.myweb.job_portal.dto.request;

import lombok.Data;

@Data
public class JobApplicationRequest {
    private Long jobPostId;
    private Long userId;
}
