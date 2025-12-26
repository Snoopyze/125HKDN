package com.myweb.job_portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProjectRequest {
    @NotNull
    private String tittle;

    @NotNull
    private Long companyId;
}
