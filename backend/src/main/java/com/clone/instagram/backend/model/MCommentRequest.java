package com.clone.instagram.backend.model;

import lombok.Data;

import java.util.Date;

@Data
public class MCommentRequest {
    private String postId;
    private String message;
}
