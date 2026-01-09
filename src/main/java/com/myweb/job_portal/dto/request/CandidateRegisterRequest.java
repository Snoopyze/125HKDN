package com.myweb.job_portal.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateRegisterRequest {
    private String fullName;
    private String account;
    private String password;
    private Integer gender;
}
