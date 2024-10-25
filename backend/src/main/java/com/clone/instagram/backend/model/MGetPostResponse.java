package com.clone.instagram.backend.model;

import com.clone.instagram.backend.entity.Comment;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MGetPostResponse {
    private String id;
    private String description;
    private List<String> images;
    private Date createdAt;

    private String userId;
    private String username;
    private String userProfile;

    private Integer likeCount;
    private Boolean isLikedByCurrentUser;

    private Boolean isSavedByCurrentUser;

    private List<MCommentResponse> comments;
}
