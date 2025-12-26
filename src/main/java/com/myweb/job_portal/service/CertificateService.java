package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.response.CertificateResponse;
import com.myweb.job_portal.entity.CandidateCertificate;
import com.myweb.job_portal.entity.CandidateProfiles;
import com.myweb.job_portal.entity.Companies;
import com.myweb.job_portal.repository.CandidateCertificateRepository;
import com.myweb.job_portal.repository.CandidateProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateService {

    @Autowired
    CandidateCertificateRepository candidateCertificateRepository;

    @Autowired
    CandidateProfileRepository candidateProfileRepository;

    public List<CertificateResponse> getMyCertificates(Long userId) {

        CandidateProfiles profiles = candidateProfileRepository.findByUsers_Id(userId)
                .orElseThrow(() -> new RuntimeException("Chưa cập nhật hồ sơ cá nhân!"));

        String fullName = profiles.getFullName();

        List<CandidateCertificate> certs = candidateCertificateRepository.findByUserId(userId);

        return certs.stream().map(cert -> {

            Companies companies = cert.getJobApplications().getJobPosts().getCompanyProjects().getCompany();

            return CertificateResponse.builder()
                    .id(cert.getId())
                    .certificateUrl(cert.getCertificateUrl())
                    .createdAt(cert.getCreatedAt())
                    .fullName(fullName)
                    .projectTitle(cert.getJobApplications().getJobPosts().getCompanyProjects().getTittle())
                    .ownerName(cert.getJobApplications().getJobPosts().getCompanyProjects().getCompany().getOwnerName())
                    .build();


        }).collect(Collectors.toList());
    }


}
