package com.clone.instagram.backend.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MMessageRequest {
    private String conversationId;
    private String senderId;
    private String content;
}
