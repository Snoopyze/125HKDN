package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.ConversationLastMessageDTO;
import com.myweb.job_portal.dto.response.ConversationCandidateResponse;
import com.myweb.job_portal.entity.Conversation;
import com.myweb.job_portal.repository.ConversationRepository;
import com.myweb.job_portal.utils.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final CurrentUserUtil currentUserUtil;

//    public List<ConversationCandidateResponse> getMyConversations() {
//        Long userId = currentUserUtil.getCurrentUserId();
//        List<Conversation> conversations = conversationRepository.findByUserId(userId);
//
//        return  conversations.stream()
//                .map(c -> ConversationCandidateResponse.builder()
//                        .conversationId(c.getId())
//                        .jobApplicationId(c.getJobApplications().getId())
//
//                        .build())
//                .toList();
//    }
//
//    public boolean isUserInConversation(Long conversationId) {
//        Long userId = currentUserUtil.getCurrentUserId();
//        return conversationRepository.findByUserId(userId)
//                .stream()
//                .anyMatch(c -> c.getId().equals(conversationId));
//    }

    public List<ConversationLastMessageDTO> getMyConversationsLastMessage() {
        Long userId = currentUserUtil.getCurrentUserId();
        return conversationRepository.findConversationWithLastMessage(userId);
    }
}
