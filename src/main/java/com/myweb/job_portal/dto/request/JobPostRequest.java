package com.myweb.job_portal.dto.request;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
public class JobPostRequest {

    @NotNull
    private Long projectId;

    @NotNull
    private Long positionId;

    @NotNull
    private String tittle;

    private LocalDate applicationDeadline;

    @NotNull
    private Integer quantity;

    private String jobDescription;
}
