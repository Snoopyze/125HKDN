package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.CandidateCertificate;
import com.myweb.job_portal.entity.CandidateProfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateProfileRepository extends JpaRepository<CandidateProfiles,Long> {

    Optional<CandidateProfiles> findByUsers_Id(Long userId);


}
