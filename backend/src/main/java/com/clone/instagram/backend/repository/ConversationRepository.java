package com.clone.instagram.backend.repository;

import com.clone.instagram.backend.entity.Conversation;
import org.springframework.data.repository.CrudRepository;

public interface ConversationRepository extends CrudRepository<Conversation, String> {
}
