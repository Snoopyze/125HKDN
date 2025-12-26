package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.request.CompanyProjectRequest;
import com.myweb.job_portal.dto.response.CompanyProjectResponse;

import java.util.List;

public interface CompanyProjectsService {

	CompanyProjectResponse getProjectById(long id);

	List<CompanyProjectResponse> getAllProjects();

	List<CompanyProjectResponse> getProjectsByCompanyId(Long companyId);

	CompanyProjectResponse createProject(CompanyProjectRequest request);

	void deleteProject(Long projectId);

	CompanyProjectResponse updateProject(Long projectId, CompanyProjectRequest request);
}
