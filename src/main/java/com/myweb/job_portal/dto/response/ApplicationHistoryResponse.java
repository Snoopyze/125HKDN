package com.myweb.job_portal.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationHistoryResponse {
    private Long applicationId;
    private String status;

    private Long jobPostId;
    private String jobPostName;
    private String jobPostDescription;
    private LocalDate applicationDeadline;

    private Long positionId;
    private String positionName;

    private Long projectId;
    private String projectName;

    private Long companyId;
    private String legalName;
    private String provinceAddress;
    private String logoUrl;

}
