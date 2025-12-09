package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Conversations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversations, Long> {
}
