package com.myweb.job_portal.controller;

import com.myweb.job_portal.service.ConversationService;
import com.myweb.job_portal.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;
    private final MessageService messageService;

    @GetMapping
    public Object getAllConversations() {
        return conversationService.getMyConversations();
    }

    @GetMapping("/{conversationId}/messages")
    public Object getMessages(
            @PathVariable("conversationId") Long conversationId,
            @RequestParam int page,
            @RequestParam int size) {
        return messageService.getMessages(conversationId, page, size);
    }

    @PutMapping("/{conversationId}/read")
    public void markAsread(@PathVariable("conversationId") Long conversationId) {
        messageService.markAsread(conversationId);
    }
}
