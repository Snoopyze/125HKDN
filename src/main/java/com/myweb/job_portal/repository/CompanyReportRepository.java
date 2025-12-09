package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.CompanyReports;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyReportRepository extends JpaRepository<CompanyReports,Long> {
}
