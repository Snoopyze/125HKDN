package com.myweb.job_portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myweb.job_portal.enums.MessageTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonIgnore
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    @JsonIgnore
    private Conversation conversation;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MessageTypeEnum messageTypeEnum;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt =  LocalDateTime.now();

}
