package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Messages,Long> {
}
