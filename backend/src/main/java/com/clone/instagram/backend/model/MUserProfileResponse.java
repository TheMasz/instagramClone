package com.clone.instagram.backend.model;

import lombok.Data;

@Data
public class MUserProfileResponse {
    private String id;
    private String email;
    private String username;
    private String bio;
    private String profilePictureUrl;
    private Boolean followedByCurrentUser;
    private Integer followerCount;
    private Integer followingCount;
}
