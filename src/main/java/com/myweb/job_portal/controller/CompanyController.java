package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.response.CompanyDetailResponse;
import com.myweb.job_portal.dto.response.CompanyListResponse;
import com.myweb.job_portal.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("/list")
    public ResponseEntity<List<CompanyListResponse>> getList() {
        return ResponseEntity.ok(companyService.getListCompanies());
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<CompanyDetailResponse> getDetail(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(companyService.getCompanyDetail(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
