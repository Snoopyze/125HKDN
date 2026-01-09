package com.myweb.job_portal.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CertificateResponse {
    private Long id;
    private Long jobPostsId;
    private String certificateUrl;
    private LocalDateTime createdAt;
    private String fullName;
    private String projectTitle;
    private String ownerName;
}
