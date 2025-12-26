package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.CompanyProjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyProjectRepository extends JpaRepository<CompanyProjects,Long> {
    List<CompanyProjects> findByCompany_Id(Long companyId);
}
