package com.myweb.job_portal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProjectResponse {
    private Long id;
    private String tittle;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long companyId;
}
