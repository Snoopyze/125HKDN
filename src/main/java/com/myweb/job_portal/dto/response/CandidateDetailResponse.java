package com.myweb.job_portal.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CandidateDetailResponse {
    private Long userId;
    private String email;
    private String phone;
    private String password;
    private String role;
    private String status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private Long id;
    private String fullName;
    private String avatarUrl;
    private String streetAddress;
    private String wardAddress;
    private String provinceAddress;



}
