package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Positions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Positions,Long> {
}
