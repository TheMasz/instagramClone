package com.clone.instagram.backend.model;

import lombok.Data;

import java.util.Date;

@Data
public class MCommentResponse {
    private String commentId;
    private String message;
    private Date createdAt;

    private String username;
    private String profilePictureUrl;
}
