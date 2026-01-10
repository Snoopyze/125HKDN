package com.myweb.job_portal.repository;

import com.myweb.job_portal.dto.MessageHistoryDTO;
import com.myweb.job_portal.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    @Query("""
        select new com.myweb.job_portal.dto.MessageHistoryDTO(
            m.id,
            u.id,
            m.content,
            m.messageTypeEnum,
            m.isRead,
            m.createdAt
        )
        from Message m
        join m.sender u
        where m.conversation.id = :conversationId
        order by m.createdAt asc 
""")
    Page<MessageHistoryDTO> findMess(
            @Param("conversationId") Long conversationId,
            Pageable pageable
    );

    @Modifying
    @Query("""
        UPDATE Message m
        SET m.isRead = true
        WHERE m.conversation.id = :conversationId
          AND m.sender.id <> :userId
          AND m.isRead = false
    """)
    void markMessagesAsRead(Long conversationId, Long senderId);
}
