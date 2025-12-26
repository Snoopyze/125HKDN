package com.myweb.job_portal.dto.request;

import com.myweb.job_portal.enums.VerificationsStatus;
import lombok.Data;

@Data
public class CompanyVerificationRequest {

    private Long companyId;
    private String taxCode;
    private String reviewerNote;
    private VerificationsStatus status;
}
