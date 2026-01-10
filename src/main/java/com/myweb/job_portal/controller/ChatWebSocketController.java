//package com.myweb.job_portal.controller;
//
//import com.myweb.job_portal.dto.request.SendFileMessageRequest;
//import com.myweb.job_portal.dto.request.SendMessageRequest;
//import com.myweb.job_portal.dto.response.MessageResponse;
//import com.myweb.job_portal.service.MessageService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@Controller
//@RequiredArgsConstructor
//public class ChatWebSocketController {
//
//    private final MessageService messageService;
//    private final SimpMessagingTemplate messagingTemplate;
//
//    @MessageMapping("/chat.sendText")
//    public void sendMessageText(@Payload SendMessageRequest request) {
//        var message = messageService.sendMessageText(
//                request.getConversationId(),
//                request.getContent()
//        );
//
//        messagingTemplate.convertAndSend(
//                "/topic/conversations/" + request.getConversationId(),
//                MessageResponse.builder()
//                        .id(message.getId())
//                        .senderId(message.getSender().getId())
//                        .content(message.getContent())
//                        .isRead(false)
//                        .createdAt(message.getCreatedAt())
//                        .messageType(message.getMessageTypeEnum())
//                        .build()
//        );
//    }
//
//
//
//}
