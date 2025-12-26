package com.myweb.job_portal.dto.response;

import com.myweb.job_portal.entity.CompanyVerifications;
import com.myweb.job_portal.enums.VerificationsStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CompanyVerificationResponse {

    private Long id;
    private Long companyId;
    private String taxCode;
    private String businessLicenseUrl;
    private VerificationsStatus status;
    private LocalDateTime reviewedAt;
    private String reviewerNote;
    public static CompanyVerificationResponse fromEntity(
            CompanyVerifications entity
    ) {
        return new CompanyVerificationResponse(
                entity.getId(),
                entity.getCompany().getId(),
                entity.getTaxCode(),
                entity.getBusinessLicenseUrl(),
                entity.getVerificationsStatus(),
                entity.getReviewedAt(),
                entity.getReviewerNote()
        );
    }
}
