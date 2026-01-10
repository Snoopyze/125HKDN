package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.SendMessageRequest;
import com.myweb.job_portal.entity.Message;
import com.myweb.job_portal.enums.MessageTypeEnum;
import com.myweb.job_portal.repository.MessageRepository;
import com.myweb.job_portal.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{conversationId}/messages")
    public Object getMessages(
            @PathVariable("conversationId") Long conversationId,
            @RequestParam int page,
            @RequestParam int size) {
        return messageService.getMessages(conversationId, page, size);
    }


    @PostMapping("/send-message/text")
    public Object sendMessage(@RequestBody SendMessageRequest request) {
        try {
            return messageService.sendMessageText(
                    request.getConversationId(),
                    request.getContent()
            );
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
