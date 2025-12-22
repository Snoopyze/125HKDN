package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.JobPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostRepository extends JpaRepository<JobPosts,Long> {

    @Query("select j from JobPosts j " +
            "join fetch j.companyProjects p " +
            "join fetch p.company c ")
    List<JobPosts> findAllJobsWithDetails();

    @Query("select j from JobPosts j " +
            "join  fetch j.companyProjects p " +
            "join  fetch  p.company c " +
            "join  fetch  j.positions pos " +
            "where (:posId is null or pos.id = :posId) " +
            "and (:province is null or c.provinnceAddress like concat('%',:province,'%')) " +
            "and (:companyId is null or c.id = :companyId)")
    List<JobPosts> searchJobPosts(@Param("posId") Long posId,
                                  @Param("province") String province,
                                  @Param("companyId") Long companyId);

}
