package com.clone.instagram.backend.business;

import com.clone.instagram.backend.entity.Conversation;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.exception.ChatException;
import com.clone.instagram.backend.model.MConversationResponse;
import com.clone.instagram.backend.model.MNewConversationRequest;
import com.clone.instagram.backend.model.MNewConversationResponse;
import com.clone.instagram.backend.service.ConversationService;
import com.clone.instagram.backend.util.SecurityUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ConversationBusiness {
    private final SimpMessagingTemplate template;
    private final ConversationService conversationService;

    public ConversationBusiness(SimpMessagingTemplate template, ConversationService conversationService) {
        this.template = template;
        this.conversationService = conversationService;
    }

//    public void post(MChatMessageRequest request) throws BaseException {
//        final String destination = "/topic/chat";
//
//        Optional<String> opt = SecurityUtil.getCurrentUserId();
//
//        if(opt.isEmpty()){
//            throw ChatException.accessDenied();
//        }
//
//        // validate message
//        ChatMessage payload = new ChatMessage();
//        payload.setFrom(opt.get());
//        payload.setMessage(request.getMessage());
//
//
//        template.convertAndSend(destination, payload);
//    }

    public MConversationResponse getConversation(String id) throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();

        if (opt.isEmpty()) {
            throw ChatException.accessDenied();
        }

        Optional<Conversation> optConversation = conversationService.getById(id);
        if(optConversation.isEmpty()){

        }

        Conversation conversation = optConversation.get();
        MConversationResponse response = new MConversationResponse();
        response.setConversationId(conversation.getId());
        response.setMessages(conversation.getMessages());
        response.setParticipants(conversation.getParticipants());
        response.setCreatedAt(conversation.getCreatedAt());
        return response;
    }

    public MNewConversationResponse newConversation(MNewConversationRequest request) throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();

        if (opt.isEmpty()) {
            throw ChatException.accessDenied();
        }

        if (request.getParticipantIds().isEmpty()) {

        }

        Conversation newConversation = conversationService.create(request.getParticipantIds(), new Date());

        MNewConversationResponse response = new MNewConversationResponse();
        response.setConversationId(newConversation.getId());
        response.setParticipantIds(newConversation.getParticipants());
        response.setCreatedAt(newConversation.getCreatedAt());
        return response;

    }


}
