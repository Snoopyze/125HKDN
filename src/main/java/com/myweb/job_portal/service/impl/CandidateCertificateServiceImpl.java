package com.myweb.job_portal.service.impl;

import com.myweb.job_portal.dto.request.CandidateCertificateRequest;
import com.myweb.job_portal.dto.response.CandidateCertificateResponse;
import com.myweb.job_portal.entity.CandidateCertificate;
import com.myweb.job_portal.entity.JobApplications;
import com.myweb.job_portal.entity.Users;
import com.myweb.job_portal.repository.CandidateCertificateRepository;
import com.myweb.job_portal.repository.JobApplicationRepository;
import com.myweb.job_portal.repository.UserRepository;
import com.myweb.job_portal.service.CandidateCertificateService;
import com.myweb.job_portal.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateCertificateServiceImpl
        implements CandidateCertificateService {

    private final CandidateCertificateRepository repository;
    private final UserRepository userRepository;
    private final JobApplicationRepository jobApplicationsRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<CandidateCertificateResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(CandidateCertificateResponse::fromEntity)
                .toList();
    }

    @Override
    public CandidateCertificateResponse getById(Long id) {
        CandidateCertificate entity = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("CandidateCertificate not found"));
        return CandidateCertificateResponse.fromEntity(entity);
    }

    @Override
    public List<CandidateCertificateResponse> getByUser(Long userId) {
        return repository.findByUsers_Id(userId)
                .stream()
                .map(CandidateCertificateResponse::fromEntity)
                .toList();
    }

    @Override
    public List<CandidateCertificateResponse> getByJobApplication(
            Long jobApplicationId
    ) {
        return repository.findByJobApplications_Id(jobApplicationId)
                .stream()
                .map(CandidateCertificateResponse::fromEntity)
                .toList();
    }

    @Override
    public CandidateCertificateResponse create(CandidateCertificateRequest request, MultipartFile certificate
    ) {
        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        JobApplications jobApplication =
                jobApplicationsRepository.findById(
                        request.getJobApplicationId()
                ).orElseThrow(() ->
                        new RuntimeException("Job application not found"));

        String certificateUrl = cloudinaryService.uploadAndGetUrl(
                certificate,
                "candidate/certificates"
        );

        CandidateCertificate entity =
                CandidateCertificate.builder()
                        .users(user)
                        .jobApplications(jobApplication)
                        .certificateUrl(certificateUrl)
                        .createdAt(LocalDateTime.now())
                        .build();

        return CandidateCertificateResponse.fromEntity(
                repository.save(entity)
        );
    }

    @Override
    public CandidateCertificateResponse update(
            Long id,
            MultipartFile certificate
    ) {
        CandidateCertificate entity = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("CandidateCertificate not found"));

        String certificateUrl = cloudinaryService.uploadAndGetUrl(
                certificate,
                "candidate/certificates"
        );

        entity.setCertificateUrl(certificateUrl);

        return CandidateCertificateResponse.fromEntity(
                repository.save(entity)
        );
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("CandidateCertificate not found");
        }
        repository.deleteById(id);
    }
}
