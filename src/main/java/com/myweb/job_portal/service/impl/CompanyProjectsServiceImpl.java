package com.myweb.job_portal.service.impl;

import com.myweb.job_portal.dto.request.CompanyProjectRequest;
import com.myweb.job_portal.dto.response.CompanyProjectResponse;
import com.myweb.job_portal.entity.Companies;
import com.myweb.job_portal.entity.CompanyProjects;
import com.myweb.job_portal.repository.CompaniesRepository;
import com.myweb.job_portal.repository.CompanyProjectRepository;
import com.myweb.job_portal.service.CompanyProjectsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CompanyProjectsServiceImpl implements CompanyProjectsService {

    private final CompanyProjectRepository repository;
    private final CompaniesRepository  companyRepository;
    public CompanyProjectsServiceImpl(CompanyProjectRepository repository , CompaniesRepository companyRepository) {
        this.repository = repository;
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyProjectResponse getProjectById(long id) {
    	CompanyProjects project = repository.findById(id)
    	        .orElseThrow(() -> new RuntimeException("Project not found"));

        return new CompanyProjectResponse(
                project.getId(),
                project.getTittle(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                project.getCompany().getId()
        );
    }
    @Override
    public List<CompanyProjectResponse> getAllProjects() {

        List<CompanyProjects> projects = repository.findAll();

        return projects.stream()
                .map(project -> new CompanyProjectResponse(
                        project.getId(),
                        project.getTittle(),
                        project.getCreatedAt(),
                        project.getUpdatedAt(),
                        project.getCompany().getId()
                ))
                .collect(Collectors.toList());
    }
    @Override
    public List<CompanyProjectResponse> getProjectsByCompanyId(Long companyId) {
        return repository.findByCompany_Id(companyId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private CompanyProjectResponse mapToResponse(CompanyProjects project) {
        return new CompanyProjectResponse(
                project.getId(),
                project.getTittle(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                project.getCompany().getId()
        );
    }

    @Override
    public CompanyProjectResponse createProject(CompanyProjectRequest request) {

        Companies company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        CompanyProjects project = CompanyProjects.builder()
                .tittle(request.getTittle())
                .company(company)
                .build();

        CompanyProjects saved = repository.save(project);

        return new CompanyProjectResponse(
                saved.getId(),
                saved.getTittle(),
                saved.getCreatedAt(),
                saved.getUpdatedAt(),
                saved.getCompany().getId()
        );
    }
    @Override
    public void deleteProject(Long projectId) {

        CompanyProjects project = repository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        repository.delete(project);
    }
    @Override
    public CompanyProjectResponse updateProject(
            Long projectId,
            CompanyProjectRequest request) {

        CompanyProjects project = repository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Companies company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        project.setTittle(request.getTittle());
        project.setCompany(company);
        project.setUpdatedAt(LocalDateTime.now());

        CompanyProjects saved = repository.save(project);

        return new CompanyProjectResponse(
                saved.getId(),
                saved.getTittle(),
                saved.getCreatedAt(),
                saved.getUpdatedAt(),
                saved.getCompany().getId()
        );
    }

}
