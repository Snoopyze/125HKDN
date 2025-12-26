package com.myweb.job_portal.service.impl;

import com.myweb.job_portal.dto.request.CompanyRequest;
import com.myweb.job_portal.dto.response.CompanyResponseByAn;
import com.myweb.job_portal.entity.Companies;
import com.myweb.job_portal.entity.Users;
import com.myweb.job_portal.repository.CompaniesRepository;
import com.myweb.job_portal.repository.UserRepository;
import com.myweb.job_portal.service.CloudinaryService;
import com.myweb.job_portal.service.CompaniesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompaniesServiceImpl implements CompaniesService {

    private final CompaniesRepository companiesRepository;
    private final UserRepository usersRepository;
    private final CloudinaryService cloudinaryService;
    @Override
    public List<CompanyResponseByAn> getAll() {
        return companiesRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CompanyResponseByAn getById(Long id) {
        Companies company = companiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return toResponse(company);
    }

    @Override
    public CompanyResponseByAn create(CompanyRequest request, MultipartFile logo) {

        Users owner = usersRepository.findById(request.getOwnerUserId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Companies company = Companies.builder()
                .owner(owner)
                .ownerName(request.getOwnerName())
                .legalName(request.getLegalName())
                .shortName(request.getShortName())
                .streetAddress(request.getStreetAddress())
                .wardAddress(request.getWardAddress())
                .provinnceAddress(request.getProvinnceAddress())
                .websiteUrl(request.getWebsiteUrl())
                .industry(request.getIndustry())
                .companySize(request.getCompanySize())
                .description(request.getDescription())
                .build();

        if (logo != null && !logo.isEmpty()) {
            String logoUrl = cloudinaryService.uploadAndGetUrl(
                    logo, "companies/logo"
            );
            company.setLogoUrl(logoUrl);
        }

        return CompanyResponseByAn.fromEntity(
                companiesRepository.save(company)
        );
    }

    @Override
    public CompanyResponseByAn updateInfo(Long id, CompanyRequest request) {

        Companies company = companiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        company.setOwnerName(request.getOwnerName());
        company.setLegalName(request.getLegalName());
        company.setShortName(request.getShortName());
        company.setStreetAddress(request.getStreetAddress());
        company.setWardAddress(request.getWardAddress());
        company.setProvinnceAddress(request.getProvinnceAddress());
        company.setWebsiteUrl(request.getWebsiteUrl());
        company.setIndustry(request.getIndustry());
        company.setCompanySize(request.getCompanySize());
        company.setDescription(request.getDescription());

        return CompanyResponseByAn.fromEntity(
                companiesRepository.save(company)
        );
    }

    @Override
    public CompanyResponseByAn updateLogo(Long id, MultipartFile logo) {

        Companies company = companiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        String logoUrl = cloudinaryService.uploadAndGetUrl(
                logo,
                "companies/logo"
        );

        company.setLogoUrl(logoUrl);

        return CompanyResponseByAn.fromEntity(
                companiesRepository.save(company)
        );
    }


    @Override
    public void delete(Long id) {
        if (!companiesRepository.existsById(id)) {
            throw new RuntimeException("Company not found");
        }
        companiesRepository.deleteById(id);
    }

    private CompanyResponseByAn toResponse(Companies company) {
        return new CompanyResponseByAn(
                company.getId(),
                company.getOwner().getId(),
                company.getOwnerName(),
                company.getLegalName(),
                company.getShortName(),
                company.getStreetAddress(),
                company.getWardAddress(),
                company.getProvinnceAddress(),
                company.getWebsiteUrl(),
                company.getIndustry(),
                company.getCompanySize(),
                company.getDescription(),
                company.getLogoUrl()
        );
    }
}
