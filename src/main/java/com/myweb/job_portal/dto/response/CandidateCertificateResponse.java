package com.myweb.job_portal.dto.response;

import com.myweb.job_portal.entity.CandidateCertificate;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CandidateCertificateResponse {

    private Long id;
    private Long userId;
    private Long jobApplicationId;
    private String certificateUrl;
    private LocalDateTime createdAt;

    public static CandidateCertificateResponse fromEntity(
            CandidateCertificate entity
    ) {
        return new CandidateCertificateResponse(
                entity.getId(),
                entity.getUsers().getId(),
                entity.getJobApplications().getId(),
                entity.getCertificateUrl(),
                entity.getCreatedAt()
        );
    }
}
