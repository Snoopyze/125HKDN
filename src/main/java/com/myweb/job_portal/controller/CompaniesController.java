package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.CompanyRequest;
import com.myweb.job_portal.dto.response.CompanyResponseByAn;
import com.myweb.job_portal.service.CompaniesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompaniesController {

    private final CompaniesService companiesService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public List<CompanyResponseByAn> getAll() {
        return companiesService.getAll();
    }

    @GetMapping("/{id}")
    public CompanyResponseByAn getById(@PathVariable Long id) {
        return companiesService.getById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompanyResponseByAn createCompany(
            @RequestPart("data") String data,
            @RequestPart(value = "logo", required = false) MultipartFile logo
    ) throws IOException {

        CompanyRequest request =
                objectMapper.readValue(data, CompanyRequest.class);

        return companiesService.create(request, logo);
    }

    @PutMapping("/{id}")
    public CompanyResponseByAn updateCompanyInfo(
            @PathVariable Long id,
            @RequestBody CompanyRequest request
    ) {
        return companiesService.updateInfo(id, request);
    }

    @PutMapping(value = "/{id}/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public CompanyResponseByAn updateCompanyLogo(
            @PathVariable Long id,
            @RequestPart("logo") MultipartFile logo
    ) {
        return companiesService.updateLogo(id, logo);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        companiesService.delete(id);
    }
}

