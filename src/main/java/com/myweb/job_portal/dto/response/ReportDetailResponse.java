package com.myweb.job_portal.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDetailResponse {
    private Long reportId;
    private String description;
    private String evidenceUrl;
    private String adminNote;
    private String status;


    private String reporterName;
}
