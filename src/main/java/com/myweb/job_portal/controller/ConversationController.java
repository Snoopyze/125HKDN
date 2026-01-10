package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.ConversationLastMessageDTO;
import com.myweb.job_portal.service.ConversationService;
import com.myweb.job_portal.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;
    private final MessageService messageService;

    @GetMapping("/all-conversation")
    public List<ConversationLastMessageDTO> getCandidateConversations() {
        return conversationService.getMyConversationsLastMessage();
    }
}
