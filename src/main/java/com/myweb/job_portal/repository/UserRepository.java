package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
}
