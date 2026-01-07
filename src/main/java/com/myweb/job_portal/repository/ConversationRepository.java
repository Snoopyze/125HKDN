package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("""
        SELECT c
        FROM Conversation c
        JOIN c.jobApplications ja
        WHERE ja.users.id = :userId
    """)
    List<Conversation> findByUserId(Long userId);
}
