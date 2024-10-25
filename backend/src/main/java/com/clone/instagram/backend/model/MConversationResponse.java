package com.clone.instagram.backend.model;

import com.clone.instagram.backend.entity.Message;
import com.clone.instagram.backend.entity.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MConversationResponse {
    private String conversationId;
    private List<User> participants;
    private List<Message> messages;
    private Date createdAt;
}
