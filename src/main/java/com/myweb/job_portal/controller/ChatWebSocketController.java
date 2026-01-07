package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.SendMessageRequest;
import com.myweb.job_portal.dto.response.MessageResponse;
import com.myweb.job_portal.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload SendMessageRequest request,
                            @Header("sender-id") String senderIdStr) {

        System.out.println(">>> Đã nhận tin nhắn từ Socket: " + request);

        try {
            Long senderId = Long.parseLong(senderIdStr);
            // 2. Gọi Service lưu DB
            MessageResponse savedMessage = messageService.sendMessage(request, senderId);
            System.out.println(">>> Đã lưu vào DB thành công. ID tin nhắn: " + savedMessage.getId());

            // 3. Gửi lại cho Client
            simpMessagingTemplate.convertAndSend(
                    "/topic/conversations/" + request.getConversationId(),
                    savedMessage
            );
        } catch (Exception e) {
            // 4. NẾU LỖI, NÓ SẼ HIỆN Ở ĐÂY
            System.err.println(">>> LỖI XỬ LÝ MESSAGE: ");
            e.printStackTrace(); // Quan trọng: In lỗi ra console server
        }

//        var message = messageService.sendMessage(
//                request.getConversationId(),
//                request.getContent()
//        );
//
//        simpMessagingTemplate.convertAndSend("/topic/conversations" + request.getConversationId(), message);
    }


}
