package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Companies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompaniesRepository extends JpaRepository<Companies,Long> {

    @Query("select distinct c from Companies c " +
            "left join fetch c.projects p ")
    List<Companies> findAllCompanies();
}
