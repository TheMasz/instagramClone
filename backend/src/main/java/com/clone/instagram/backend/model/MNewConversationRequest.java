package com.clone.instagram.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class MNewConversationRequest {
   private List<String> participantIds;
}
