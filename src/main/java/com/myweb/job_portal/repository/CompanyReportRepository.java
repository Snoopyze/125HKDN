package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.CompanyReports;
import com.myweb.job_portal.enums.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyReportRepository extends JpaRepository<CompanyReports,Long> {

    @Query("select r from CompanyReports  r " +
            "join fetch r.users u " +
            "left join fetch  u.candidateProfile cp " +
            "order by r.createdAt desc"
    )
    List<CompanyReports> findAllReportsWithDetails();

    @Query("select r from CompanyReports  r " +
            "join fetch r.users u " +
            "left join fetch u.candidateProfile " +
            "where r.id = :id")
    Optional<CompanyReports> findDetailById(@Param("id") Long id);

    long countByStatus(ReportStatus status);
}
