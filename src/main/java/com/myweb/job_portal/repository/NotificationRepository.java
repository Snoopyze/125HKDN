package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications,Long> {
}
