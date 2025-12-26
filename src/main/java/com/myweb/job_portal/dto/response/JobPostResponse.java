package com.myweb.job_portal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class JobPostResponse {

    private Long id;
    private String tittle;
    private LocalDate applicationDeadline;
    private Integer quantity;
    private String jobDescription;
    private Long projectId;
    private Long positionId;
}
