package com.myweb.job_portal.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRegisterRequest {
    private String email;
    private String password;
    private String ownerName;
    private Integer gender;
    private String legalName;
    private String taxCode;
    private String province;
    private String ward;
    private String street;
    private String industry;
    private String companySize;
}
