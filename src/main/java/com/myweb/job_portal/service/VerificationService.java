package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.response.VerificationResponse;
import com.myweb.job_portal.entity.CompanyVerifications;
import com.myweb.job_portal.enums.VerificationsStatus;
import com.myweb.job_portal.repository.CompanyVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VerificationService {

    @Autowired
    CompanyVerificationRepository companyVerificationRepository;

    public List<VerificationResponse> getPendingVerifications(){
        List<CompanyVerifications> verificationResponseList = companyVerificationRepository.findAllByStatus(VerificationsStatus.pending);

        return verificationResponseList.stream().map(cv ->
                VerificationResponse.builder()
                        .verificationId(cv.getId())
                        .taxCode(cv.getTaxCode())
                        .businessLicenseUrl(cv.getBusinessLicenseUrl())
                        .status(cv.getVerificationsStatus().name())
                        .companyId(cv.getCompany().getId())
                        .companyShortName(cv.getCompany().getShortName())
                        .build()
        ).collect(Collectors.toList());
    }

    @Transactional
    public void approveVerification(Long verificationId){

        CompanyVerifications verification = companyVerificationRepository.findById(verificationId)
                .orElseThrow(() -> new RuntimeException("Yêu cầu xác minh không tồn tại!"));

        if (verification.getVerificationsStatus() != VerificationsStatus.pending) {
            throw new RuntimeException("Yêu cầu này đã được xử lý trước đó!");
        }

        verification.setVerificationsStatus(VerificationsStatus.accepted);
        verification.setReviewerNote("Đã duyệt");
        verification.setReviewedAt(LocalDateTime.now());

        companyVerificationRepository.save(verification);
    }

    @Transactional
    public void rejectVerification(Long verificationId, String reason) {
        CompanyVerifications verification = companyVerificationRepository.findById(verificationId)
                .orElseThrow(() -> new RuntimeException("Yêu cầu xác minh không tồn tại!"));

        verification.setVerificationsStatus(VerificationsStatus.rejected);
        verification.setReviewerNote(reason);
        verification.setReviewedAt(LocalDateTime.now());

        companyVerificationRepository.save(verification);
    }
}
