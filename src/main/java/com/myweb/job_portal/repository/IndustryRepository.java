package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Industries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industries,Long> {
}
