package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.request.SendMessageRequest;
import com.myweb.job_portal.dto.response.MessageResponse;
import com.myweb.job_portal.entity.Conversation;
import com.myweb.job_portal.entity.Message;
import com.myweb.job_portal.entity.Users;
import com.myweb.job_portal.enums.MessageTypeEnum;
import com.myweb.job_portal.repository.ConversationRepository;
import com.myweb.job_portal.repository.MessageRepository;
import com.myweb.job_portal.repository.UserRepository;
import com.myweb.job_portal.utils.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final CurrentUserUtil currentUserUtil;



    public MessageResponse sendMessage( SendMessageRequest request, Long senderId) {

//        Long userId = currentUserUtil.getCurrentUserId();

        Conversation conversation = conversationRepository
                .findById(request.getConversationId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hội thoại"));

        Users sender = Users.builder()
                .id(senderId)
                .build();

        Message message = Message.builder()
                .sender(sender)
                .conversation(conversation)
                .content(request.getContent())
                .messageTypeEnum(MessageTypeEnum.text)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        messageRepository.save(message);

        return toResponse(message);
    }

    public Page<Message> getMessages(Long conversationId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return messageRepository.findMess(conversationId, pageable);
    }

    @Transactional
    public void markAsread(Long conversationId) {
        Long userId = currentUserUtil.getCurrentUserId();
        messageRepository.markMessagesAsRead(conversationId, userId);
    }

    private MessageResponse toResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .senderId(message.getSender().getId())
                .content(message.getContent())
                .isRead(message.getIsRead())
                .createdAt(message.getCreatedAt())
                .messageType(message.getMessageTypeEnum())
                .build();
    }

}
