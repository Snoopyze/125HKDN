package com.myweb.job_portal.dto.response;

import com.myweb.job_portal.enums.MessageTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {
    private Long id;
    private Long senderId;
    private String content;
    private boolean isRead;
    private LocalDateTime createdAt;
    private MessageTypeEnum messageType;
}
