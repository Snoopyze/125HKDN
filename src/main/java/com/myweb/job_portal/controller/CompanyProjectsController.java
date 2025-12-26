package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.CompanyProjectRequest;
import com.myweb.job_portal.dto.response.CompanyProjectResponse;
import com.myweb.job_portal.service.CompanyProjectsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company-projects")
public class CompanyProjectsController {

    private final CompanyProjectsService service;

    public CompanyProjectsController(CompanyProjectsService service) {
        this.service = service;
    }

    // GET /api/company-projects/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CompanyProjectResponse> getProjectById(@PathVariable Long id) {
        CompanyProjectResponse response = service.getProjectById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<CompanyProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(service.getAllProjects());
    }
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<CompanyProjectResponse>> getProjectsByCompany(
            @PathVariable Long companyId) {

        return ResponseEntity.ok(service.getProjectsByCompanyId(companyId));
    }
    @PostMapping
    public ResponseEntity<CompanyProjectResponse> createProject(
            @RequestBody @Validated CompanyProjectRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createProject(request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        service.deleteProject(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyProjectResponse> updateProject(
            @PathVariable Long id,
            @RequestBody @Validated CompanyProjectRequest request) {

        return ResponseEntity.ok(service.updateProject(id, request));
    }
}
