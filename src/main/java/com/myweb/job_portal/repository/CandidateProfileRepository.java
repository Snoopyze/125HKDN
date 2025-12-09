package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.CandidateProfiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfiles,Long> {
}
