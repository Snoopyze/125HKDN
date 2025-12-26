package com.myweb.job_portal.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobListItemResponse {

    private Long jobId;
    private String jobTitle;
    private String jobDescription;

    private Long projectId;
    private String projectTitle;

    private Long companyId;
    private String legalName;
    private String provinceAddress;
    private String logoUrl;

    private Long posId;
    private String posName;
}
