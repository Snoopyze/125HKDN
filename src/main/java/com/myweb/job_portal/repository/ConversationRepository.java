package com.myweb.job_portal.repository;

import com.myweb.job_portal.dto.ConversationLastMessageDTO;
import com.myweb.job_portal.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
//    @Query("select distinct c from Conversation c " +
//            "join fetch c.jobApplications ja " +
//            "join fetch ja.jobPosts jp " +
//            "join fetch jp.companyProjects cp " +
//            "join fetch cp.company com " +
//            "left join fetch c.messages m " +
//            "where ja.users.id = :userId"
//    )
//    List<Conversation> findByUserId(Long userId);


    @Query("""
        select new com.myweb.job_portal.dto.ConversationLastMessageDTO(
            c.id,
            ja.id,
            comp.shortName,
            comp.legalName,
            comp.logoUrl,
            m.id,
            m.content,
            m.messageTypeEnum,
            m.isRead,
            m.createdAt
        )
        from Conversation c
        join c.jobApplications ja
        join ja.jobPosts jp
        join jp.companyProjects cp
        join cp.company comp
        left join Message  m
            on m.conversation.id = c.id
            and m.createdAt = (
                select max(m2.createdAt)
                from Message m2
                where m2.conversation.id = c.id
            ) 
        where ja.users.id = :userId
        order by m.createdAt desc 
""")
    List<ConversationLastMessageDTO> findConversationWithLastMessage(@Param("userId") Long userId);
}
