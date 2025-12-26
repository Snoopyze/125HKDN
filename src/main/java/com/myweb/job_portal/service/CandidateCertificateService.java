package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.request.CandidateCertificateRequest;
import com.myweb.job_portal.dto.response.CandidateCertificateResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateCertificateService {

    List<CandidateCertificateResponse> getAll();

    CandidateCertificateResponse getById(Long id);

    List<CandidateCertificateResponse> getByUser(Long userId);

    List<CandidateCertificateResponse> getByJobApplication(Long jobAppId);

    CandidateCertificateResponse create(CandidateCertificateRequest request, MultipartFile certificate
    );

    CandidateCertificateResponse update(Long id, MultipartFile certificate
    );

    void delete(Long id);
}
