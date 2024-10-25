package com.clone.instagram.backend.model;

import lombok.Data;

@Data
public class MLikeResponse {
    private String message;
    private Integer likeCount;
    private Boolean isLikedByCurrentUser;
}
