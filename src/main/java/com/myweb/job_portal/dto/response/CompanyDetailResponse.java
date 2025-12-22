package com.myweb.job_portal.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyDetailResponse {
    private Long id;
    private String ownerName;
    private String legalName;
    private String shortName;
    private String fullAddress;
    private String websiteUrl;
    private String industry;
    private String companySize;
    private String description;
    private String logoUrl;
    private String bannerUrl;
}
