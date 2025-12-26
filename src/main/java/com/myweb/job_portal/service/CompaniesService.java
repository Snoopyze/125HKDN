package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.request.CompanyRequest;
import com.myweb.job_portal.dto.response.CompanyResponseByAn;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompaniesService {

    List<CompanyResponseByAn> getAll();

    CompanyResponseByAn getById(Long id);

    CompanyResponseByAn create(CompanyRequest request, MultipartFile logoFile);

    CompanyResponseByAn updateInfo(Long id, CompanyRequest request);

    CompanyResponseByAn updateLogo(Long id, MultipartFile logo);

    void delete(Long id);
}
