package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.JobApplications;
import com.myweb.job_portal.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplications,Long> {

    boolean existsByUsers_IdAndJobPosts_Id(Long usersId, Long jobPostsId);

    @Query( "select  a from JobApplications  a " +
            "join fetch a.jobPosts j " +
            "join fetch j.companyProjects p " +
            "join fetch p.company " +
            "join fetch j.positions " +
            "where a.users.id = :userId " +
            "and (:status is null or a.status = :status) " +
            "order by a.appliedAt desc")
    List<JobApplications> findHistory(@Param("userId") Long userId,
                                      @Param("status") ApplicationStatus status);

    @Query("select ja from JobApplications ja " +
            "join fetch ja.jobPosts jb " +
            "join fetch jb.companyProjects p " +
            "join fetch p.company c " +
            "join fetch ja.users u " +
            "join fetch u.candidateProfile cp " +
            "order by ja.appliedAt desc")
    List<JobApplications> findAllJobApplications();


    List<JobApplications> findByUsers_Id(Long userId);
    List<JobApplications> findByJobPosts_Id(Long jobPostId);
}
