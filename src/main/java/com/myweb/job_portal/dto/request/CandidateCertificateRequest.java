package com.myweb.job_portal.dto.request;

import lombok.Data;

@Data
public class CandidateCertificateRequest {
    private Long userId;
    private Long jobApplicationId;
}
