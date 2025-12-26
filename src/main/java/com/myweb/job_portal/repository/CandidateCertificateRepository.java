package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.CandidateCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateCertificateRepository extends JpaRepository<CandidateCertificate,Long> {

    @Query("select c from CandidateCertificate c " +
            "join fetch c.jobApplications ja " +
            "join fetch ja.jobPosts jp " +
            "join fetch jp.companyProjects p " +
            "join fetch p.company com " +
            "where c.users.id = :userId " +
            "order by c.createdAt desc")
    List<CandidateCertificate> findByUserId(@Param("userId") Long userId);



    List<CandidateCertificate> findByUsers_Id(Long userId);
    List<CandidateCertificate> findByJobApplications_Id(Long jobApplicationId);
}
