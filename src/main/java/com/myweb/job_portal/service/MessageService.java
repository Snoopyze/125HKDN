package com.myweb.job_portal.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.myweb.job_portal.dto.MessageHistoryDTO;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final CurrentUserUtil currentUserUtil;
    private final Cloudinary cloudinary;

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "jpg", "jpeg", "png", "gif",
            "pdf", "doc", "docx",
            "xls", "xlsx",
            "zip", "rar"
    );

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB


    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File không được để trống");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("File vượt quá dung lượng cho phép (10MB)");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new RuntimeException("Tên file không hợp lệ");
        }

        String extension = originalFilename
                .substring(originalFilename.lastIndexOf('.') + 1)
                .toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new RuntimeException("Định dạng file không được hỗ trợ");
        }
    }

//    public MessageResponse sendMessage( SendMessageRequest request, Long senderId) {
//
//        Long userId = currentUserUtil.getCurrentUserId();
//
//        Conversation conversation = conversationRepository
//                .findById(request.getConversationId())
//                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hội thoại"));
//
//        Users sender = Users.builder()
//                .id(senderId)
//                .build();
//
//        Message message = Message.builder()
//                .sender(sender)
//                .conversation(conversation)
//                .content(request.getContent())
//                .messageTypeEnum(MessageTypeEnum.text)
//                .isRead(false)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        messageRepository.save(message);
//
//        return toResponse(message);
//    }

    public Message sendMessageText(Long conversationId, String content) {
        Long userId = currentUserUtil.getCurrentUserId();
        Conversation conversation = conversationRepository
                .findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hội thoại"));

        Users user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người gửi"));

        Message message = Message.builder()
                .sender(user)
                .conversation(conversation)
                .content(content)
                .messageTypeEnum(MessageTypeEnum.text)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
        return messageRepository.save(message);
    }

    public Message sendMessageImage(Long conversationId, MultipartFile content) {
        if (content == null || content.isEmpty()) {
            throw new RuntimeException("File ảnh không được để trống");
        }
        String contentType = content.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Chỉ cho phép upload file ảnh");
        }
        String publicId = UUID.randomUUID().toString();
        String imageUrl;
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    content.getBytes(),
                    ObjectUtils.asMap(  "resource_type", "image",
                            "folder", "messages/images",
                            "public_id", publicId)
            );
            imageUrl = uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Upload fail");
        }

        Long userId = currentUserUtil.getCurrentUserId();
        Conversation conversation = conversationRepository
                .findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hội thoại"));

        Users user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người gửi"));

        Message message = Message.builder()
                .sender(user)
                .conversation(conversation)
                .content(imageUrl)
                .messageTypeEnum(MessageTypeEnum.image)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
        return messageRepository.save(message);
    }

    public Message sendMessageFile(Long conversationId, MultipartFile content) {
        validateFile(content);
        String publicId = UUID.randomUUID().toString();
        String imageUrl;
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    content.getBytes(),
                    ObjectUtils.asMap(  "resource_type", "auto",
                            "folder", "messages/files",
                            "public_id", publicId)
            );
            imageUrl = uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Upload fail");
        }

        Long userId = currentUserUtil.getCurrentUserId();
        Conversation conversation = conversationRepository
                .findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hội thoại"));

        Users user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người gửi"));

        Message message = Message.builder()
                .sender(user)
                .conversation(conversation)
                .content(imageUrl)
                .messageTypeEnum(MessageTypeEnum.file)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
        return messageRepository.save(message);
    }

    public Page<MessageHistoryDTO> getMessages(Long conversationId, int page, int size) {
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
