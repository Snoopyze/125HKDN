package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.JobApplications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplications,Long> {
}
