package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Messages,Long> {
}
