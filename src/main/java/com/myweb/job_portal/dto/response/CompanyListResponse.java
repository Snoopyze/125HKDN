package com.myweb.job_portal.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyListResponse {
    private Long id;
    private String shortName;
    private String provinceAddress;
    private String industry;
    private int totalProjects;
}
