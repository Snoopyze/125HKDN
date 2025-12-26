package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.CompanyProjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyProjectRepository extends JpaRepository<CompanyProjects,Long> {
}
