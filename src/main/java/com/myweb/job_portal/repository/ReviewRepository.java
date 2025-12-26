package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Long> {
    Optional<Reviews> findByJobApplications_Id(Long jobApplicationId);

    List<Reviews> findByReviewee_Id(Long revieweeId);

    List<Reviews> findByReviewer_Id(Long reviewerId);
}
