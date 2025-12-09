package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.JobPosts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPosts,Long> {
}
