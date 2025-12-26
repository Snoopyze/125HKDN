package com.myweb.job_portal.dto.response;

import com.myweb.job_portal.entity.Companies;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CompanyResponseByAn {

    private Long id;
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
    private String logoUrl;

    public static CompanyResponseByAn fromEntity(Companies company) {
        return CompanyResponseByAn.builder()
                .id(company.getId())
                .ownerUserId(company.getOwner().getId())
                .ownerName(company.getOwnerName())
                .legalName(company.getLegalName())
                .shortName(company.getShortName())
                .streetAddress(company.getStreetAddress())
                .wardAddress(company.getWardAddress())
                .provinnceAddress(company.getProvinnceAddress())
                .websiteUrl(company.getWebsiteUrl())
                .industry(company.getIndustry())
                .companySize(company.getCompanySize())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .build();
    }
}



