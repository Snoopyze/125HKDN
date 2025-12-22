package com.myweb.job_portal.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JobDetailResponse {

    private Long jobId;
    private String jobTitle;
    private String jobDescription;
    private Integer jobQuantity;
    private LocalDate applicationDeadline;

    private  Long positionId;
    private String positionName;


    private Long projectId;
    private String projectTitle;

    private Long companyId;
    private Long userId;
    private String ownerName;
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
