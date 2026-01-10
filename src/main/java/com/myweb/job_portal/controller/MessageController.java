package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.SendFileMessageRequest;
import com.myweb.job_portal.dto.request.SendMessageRequest;
import com.myweb.job_portal.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
            return messageService.sendMessageImage(
                    request.getConversationId(),
                    request.getContent()
            );
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
            return messageService.sendMessageFile(
                    request.getConversationId(),
                    request.getContent()
            );
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
