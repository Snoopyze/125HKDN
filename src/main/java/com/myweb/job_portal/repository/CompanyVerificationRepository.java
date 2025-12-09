package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.CompanyVerifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyVerificationRepository  extends JpaRepository<CompanyVerifications,Long> {
}
