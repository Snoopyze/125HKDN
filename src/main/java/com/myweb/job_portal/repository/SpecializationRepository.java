package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Specializations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationRepository extends JpaRepository<Specializations, Long> {
}
