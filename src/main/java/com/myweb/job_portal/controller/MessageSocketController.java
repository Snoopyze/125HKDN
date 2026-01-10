package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.SendMessageRequest;
import com.myweb.job_portal.dto.request.SendMessageSocketRequest;
import com.myweb.job_portal.dto.response.MessageResponse;
import com.myweb.job_portal.entity.Message;
import com.myweb.job_portal.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload SendMessageSocketRequest request) {

        Message message = messageService.sendMessageText(
                request.getConversationId(),
                request.getContent()
        );
        messagingTemplate.convertAndSend("/topic/messages" + request.getConversationId(), message);

    }

}
