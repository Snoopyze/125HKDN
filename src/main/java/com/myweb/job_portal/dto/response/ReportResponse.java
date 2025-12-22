package com.myweb.job_portal.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReportResponse {
    private Long reportId;
    private String description;
    private String status;
    private LocalDateTime createdAt;

    private String reporterName;

    private String accusedCompany;
}
