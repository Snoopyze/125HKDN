package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.CompanyVerifications;
import com.myweb.job_portal.enums.VerificationsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyVerificationRepository  extends JpaRepository<CompanyVerifications,Long> {

    @Query("select cv from CompanyVerifications cv " +
            "join fetch cv.company c " +
            "where cv.verificationsStatus = :status")
    List<CompanyVerifications> findAllByStatus(@Param("status") VerificationsStatus status);

}
