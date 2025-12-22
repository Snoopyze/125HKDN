package com.myweb.job_portal.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VerificationResponse {
    private Long verificationId;
    private String taxCode;
    private String businessLicenseUrl;
    private String status;

    private Long companyId;
    private String companyShortName;
}
