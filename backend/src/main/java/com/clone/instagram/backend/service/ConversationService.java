package com.clone.instagram.backend.service;

import com.clone.instagram.backend.entity.Conversation;
import com.clone.instagram.backend.repository.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {
    private final ConversationRepository repository;

    public ConversationService(ConversationRepository repository) {
        this.repository = repository;
    }

    public Conversation create(List<String> participantIds, Date createdAt) {
        Conversation newConversation = new Conversation();
//        newConversation.setParticipants(participantIds);
        newConversation.setCreatedAt(createdAt);

        return repository.save(newConversation);
    }

    public Optional<Conversation> getById(String id) {
        return repository.findById(id);
    }


}
