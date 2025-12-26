package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.response.CompanyDetailResponse;
import com.myweb.job_portal.dto.response.CompanyListResponse;
import com.myweb.job_portal.entity.Companies;
import com.myweb.job_portal.repository.CompaniesRepository;
import com.myweb.job_portal.repository.CompanyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    CompaniesRepository companiesRepository;

    public List<CompanyListResponse> getListCompanies() {

        List<Companies> companies = companiesRepository.findAllCompanies();

        return companies.stream().map(company -> {
            int count = (company.getProjects() == null ) ? 0 : company.getProjects().size();

            return CompanyListResponse.builder()
                    .id(company.getId())
                    .shortName(company.getShortName())
                    .provinceAddress(company.getProvinnceAddress())
                    .industry(company.getIndustry())
                    .totalProjects(count)
                    .build();
        }).collect(Collectors.toList());
    }

    public CompanyDetailResponse getCompanyDetail(Long id) {

        Companies company = companiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Công ty không tồn tại!"));

        String fullAddress = String.format("%s, %s, %s",
                company.getStreetAddress(),
                company.getWardAddress(),
                company.getProvinnceAddress());

        return CompanyDetailResponse.builder()
                .id(company.getId())
                .ownerName(company.getOwnerName())
                .legalName(company.getLegalName())
                .shortName(company.getShortName())
                .fullAddress(fullAddress)
                .websiteUrl(company.getWebsiteUrl())
                .industry(company.getIndustry())
                .companySize(company.getCompanySize())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .bannerUrl(company.getBannerUrl())
                .build();
    }
}
