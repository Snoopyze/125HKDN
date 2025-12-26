package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.request.CompanyVerificationRequest;
import com.myweb.job_portal.dto.response.CompanyVerificationResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyVerificationsService {

    List<CompanyVerificationResponse> getAll();

    List<CompanyVerificationResponse> getByCompany(Long companyId);

    CompanyVerificationResponse getById(Long id);

    CompanyVerificationResponse create(CompanyVerificationRequest request, MultipartFile businessLicense);

    CompanyVerificationResponse updateInfo(
            Long id,
            CompanyVerificationRequest request
    );

    // ✅ 2. Sửa / upload file license
    CompanyVerificationResponse updateLicense(
            Long id,
            MultipartFile businessLicense
    );

    void delete(Long id);
}
