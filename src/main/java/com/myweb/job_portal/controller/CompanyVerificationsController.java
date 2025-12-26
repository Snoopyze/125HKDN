package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.CompanyVerificationRequest;
import com.myweb.job_portal.dto.response.CompanyVerificationResponse;
import com.myweb.job_portal.service.CompanyVerificationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/company-verifications")
@RequiredArgsConstructor
public class CompanyVerificationsController {

    private final CompanyVerificationsService companyVerificationService;
    private final ObjectMapper objectMapper;
    // GET ALL
    @GetMapping
    public List<CompanyVerificationResponse> getAll() {
        return companyVerificationService.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public CompanyVerificationResponse getById(@PathVariable Long id) {
        return companyVerificationService.getById(id);
    }

    // GET BY COMPANY ID
    @GetMapping("/company/{companyId}")
    public List<CompanyVerificationResponse> getByCompanyId(
            @PathVariable Long companyId
    ) {
        return companyVerificationService.getByCompany(companyId);
    }

    // CREATE
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompanyVerificationResponse create(@RequestPart("data") String data,
                                              @RequestPart(value = "businessLicense", required = false)
                                                  MultipartFile businessLicense
    ) throws IOException {

        CompanyVerificationRequest request =
                objectMapper.readValue(
                        data, CompanyVerificationRequest.class
                );

        return companyVerificationService.create(
                request, businessLicense
        );
    }

    @PutMapping("/{id}")
    public CompanyVerificationResponse updateInfo(
            @PathVariable Long id,
            @RequestBody CompanyVerificationRequest request
    ) {
        return companyVerificationService.updateInfo(id, request);
    }

    @PutMapping(
            value = "/{id}/license",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public CompanyVerificationResponse updateLicense(
            @PathVariable Long id,
            @RequestPart("businessLicense") MultipartFile file
    ) {
        return companyVerificationService.updateLicense(id, file);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        companyVerificationService.delete(id);
    }
}
