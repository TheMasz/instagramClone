package com.clone.instagram.backend.model;

import lombok.Data;

@Data
public class MFollowerResponse {
    private String message;
    private Integer count;
    private Boolean isFollowedByCurrentUser;
}
