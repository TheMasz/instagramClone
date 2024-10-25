package com.clone.instagram.backend.model;

import com.clone.instagram.backend.entity.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MNewConversationResponse {
    private String conversationId;
    private List<User> participantIds;
    private Date createdAt;
}
