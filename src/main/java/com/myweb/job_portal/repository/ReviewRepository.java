package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {
}
