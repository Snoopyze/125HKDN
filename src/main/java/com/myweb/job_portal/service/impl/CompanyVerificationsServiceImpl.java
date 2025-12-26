package com.myweb.job_portal.service.impl;

import com.myweb.job_portal.dto.request.CompanyVerificationRequest;
import com.myweb.job_portal.dto.response.CompanyVerificationResponse;
import com.myweb.job_portal.entity.Companies;
import com.myweb.job_portal.entity.CompanyVerifications;
import com.myweb.job_portal.enums.VerificationsStatus;
import com.myweb.job_portal.repository.CompaniesRepository;
import com.myweb.job_portal.repository.CompanyVerificationRepository;
import com.myweb.job_portal.service.CloudinaryService;
import com.myweb.job_portal.service.CompanyVerificationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyVerificationsServiceImpl
        implements CompanyVerificationsService {

    private final CompanyVerificationRepository companyVerificationsRepository;
    private final CompaniesRepository companiesRepository;
    private final CloudinaryService cloudinaryService;

    /* ================= GET ================= */

    @Override
    public List<CompanyVerificationResponse> getAll() {
        return companyVerificationsRepository.findAll()
                .stream()
                .map(CompanyVerificationResponse::fromEntity)
                .toList();
    }

    @Override
    public List<CompanyVerificationResponse> getByCompany(Long companyId) {
        return companyVerificationsRepository.findByCompany_Id(companyId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CompanyVerificationResponse getById(Long id) {
        CompanyVerifications verification =
                companyVerificationsRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Verification not found"));

        return toResponse(verification);
    }

    /* ================= CREATE ================= */

    @Override
    public CompanyVerificationResponse create(
            CompanyVerificationRequest request,
            MultipartFile businessLicense
    ) {
        Companies company = companiesRepository.findById(
                        request.getCompanyId()
                )
                .orElseThrow(() ->
                        new RuntimeException("Company not found"));

        CompanyVerifications verification = CompanyVerifications.builder()
                .company(company)
                .taxCode(request.getTaxCode())
                .verificationsStatus(VerificationsStatus.pending)
                .build();

        if (businessLicense != null && !businessLicense.isEmpty()) {
            String licenseUrl = cloudinaryService.uploadAndGetUrl(
                    businessLicense,
                    "company_verifications/licenses"
            );
            verification.setBusinessLicenseUrl(licenseUrl);
        }

        return toResponse(
                companyVerificationsRepository.save(verification)
        );
    }

    /* ================= UPDATE ================= */

    @Override
    public CompanyVerificationResponse updateInfo(
            Long id,
            CompanyVerificationRequest request
    ) {
        CompanyVerifications entity = companyVerificationsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Verification not found"));

        entity.setTaxCode(request.getTaxCode());
        entity.setVerificationsStatus(request.getStatus());
        entity.setReviewerNote(request.getReviewerNote());

        if (request.getStatus() != VerificationsStatus.pending) {
            entity.setReviewedAt(LocalDateTime.now());
        }

        return CompanyVerificationResponse.fromEntity(
                companyVerificationsRepository.save(entity)
        );
    }

    // ===============================
    // ✅ 2. UPDATE LICENSE FILE
    // ===============================
    @Override
    public CompanyVerificationResponse updateLicense(
            Long id,
            MultipartFile businessLicense
    ) {
        CompanyVerifications entity = companyVerificationsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Verification not found"));

        String url = cloudinaryService.uploadAndGetUrl(
                businessLicense,
                "company_verifications/license"
        );

        entity.setBusinessLicenseUrl(url);

        return CompanyVerificationResponse.fromEntity(
                companyVerificationsRepository.save(entity)
        );
    }

    /* ================= DELETE ================= */

    @Override
    public void delete(Long id) {
        if (!companyVerificationsRepository.existsById(id)) {
            throw new RuntimeException("Verification not found");
        }
        companyVerificationsRepository.deleteById(id);
    }

    /* ================= RESPONSE ================= */

    private CompanyVerificationResponse toResponse(
            CompanyVerifications verification
    ) {
        CompanyVerifications savedEntity =
                companyVerificationsRepository.save(verification);
        return CompanyVerificationResponse.fromEntity(savedEntity);
    }
}
