package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Companies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompaniesRepository extends JpaRepository<Companies,Long> {
}
