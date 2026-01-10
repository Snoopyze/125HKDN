package com.myweb.job_portal.dto;

import com.myweb.job_portal.enums.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversationLastMessageDTO {
    private Long conversationId;
    private Long jobApplicationId;

    private String shortName;
    private String legalName;
    private String logoUrl;

    private Long latestMessageId;
    private String latestMessageContent;
    private MessageTypeEnum latestMessageType;
    private Boolean latestMessageIsRead;
    private LocalDateTime latestMessageCreateAt;
}
