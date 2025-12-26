package com.myweb.job_portal.dto.request;

import lombok.Data;

@Data
public class    CompanyRequest {

    private Long ownerUserId;
    private String ownerName;
    private String legalName;
    private String shortName;
    private String streetAddress;
    private String wardAddress;
    private String provinnceAddress;
    private String websiteUrl;
    private String industry;
    private String companySize;
    private String description;
}

