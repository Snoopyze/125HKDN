package com.myweb.job_portal.dto;

import com.myweb.job_portal.enums.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageHistoryDTO {
    private Long messageId;
    private Long senderId;
    private String content;
    private MessageTypeEnum messageType;
    private Boolean isRead;
    private LocalDateTime readTime;
}
