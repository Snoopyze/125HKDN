package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.ReadMessageEvent;
import com.myweb.job_portal.dto.request.SendFileMessageRequest;
import com.myweb.job_portal.dto.request.SendMessageRequest;
import com.myweb.job_portal.entity.Message;
import com.myweb.job_portal.service.MessageService;
import com.myweb.job_portal.utils.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final CurrentUserUtil currentUserUtil;

    @GetMapping("/{conversationId}/messages")
    public Object getMessages(
            @PathVariable("conversationId") Long conversationId,
            @RequestParam int page,
            @RequestParam int size) {
        return messageService.getMessages(conversationId, page, size);
    }


    @PostMapping("/send-message/text")
    public Object sendMessageText(@RequestBody SendMessageRequest request) {
        try {
            return messageService.sendMessageText(
                    request.getConversationId(),
                    request.getContent()
            );
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping(
            value = "/send-message/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Object sendMessageImage(@ModelAttribute SendFileMessageRequest request) {
        try {
            Message  message = messageService.sendMessageImage(
                    request.getConversationId(),
                    request.getContent());

            messagingTemplate.convertAndSend("/topic/conversations/" + request.getConversationId(), message);

            return message;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping(
            value = "/send-message/file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Object sendMessageFile(@ModelAttribute SendFileMessageRequest request) {
        try {
            Message  message = messageService.sendMessageFile(
                    request.getConversationId(),
                    request.getContent()
            );
            messagingTemplate.convertAndSend("/topic/conversations/" + request.getConversationId(), message);
            return messageService.sendMessageFile(
                    request.getConversationId(),
                    request.getContent()
            );
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PutMapping("/{conversationId}/read")
    public void markAsread(@PathVariable("conversationId") Long conversationId) {

        messageService.markAsread(conversationId);
        messagingTemplate.convertAndSend("/topic/conversations/" + conversationId + "/read", new ReadMessageEvent(conversationId, currentUserUtil.getCurrentUserId()));
    }

}
