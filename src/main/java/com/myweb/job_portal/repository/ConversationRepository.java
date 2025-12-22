package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Conversations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversations, Long> {


}
