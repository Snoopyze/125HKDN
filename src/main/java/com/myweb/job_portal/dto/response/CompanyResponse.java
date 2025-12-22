package com.myweb.job_portal.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CompanyResponse {
    private Long id;
    private Long userId;
    private String legalName;
    private String shortName;
    private String streetAddress;
    private String wardAddress;
    private String provinceAddress;
    private String fullAddress;
    private String websiteUrl;
    private String industry;
    private String companySize;
    private String description;
    private String logoUrl;
}
