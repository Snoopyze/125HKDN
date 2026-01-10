package com.myweb.job_portal.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationCandidateResponse {
    private Long conversationId;
    private Long jobApplicationId;

    private String shortName;
    private String legalName;
    private String logoUrl;

    private Long latestMessageId;
    private String latestMessageContent;
    private String latestMessageType;
    private String latestMessageIsRead;
    private String latestMessageCreateAt;


}
