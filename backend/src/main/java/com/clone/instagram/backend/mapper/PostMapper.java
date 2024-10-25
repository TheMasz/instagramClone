package com.clone.instagram.backend.mapper;

import com.clone.instagram.backend.entity.Comment;
import com.clone.instagram.backend.entity.Post;
import com.clone.instagram.backend.entity.PostImage;
import com.clone.instagram.backend.model.MCommentResponse;
import com.clone.instagram.backend.model.MGetPostResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    public MGetPostResponse toPostResponse(Post post, String currentUserId) {
        MGetPostResponse dto = new MGetPostResponse();

        dto.setId(post.getId());
        dto.setDescription(post.getDescription());
        dto.setCreatedAt(post.getCreatedAt());

        dto.setUserId(post.getUser().getId());
        dto.setUsername(post.getUser().getUsername());
        dto.setUserProfile(post.getUser().getProfilePictureUrl());

        List<String> imageUrls = post.getPostImages().stream().map(PostImage::getImageUrl).toList();
        dto.setImages(imageUrls);

        dto.setLikeCount(post.getLikes().size());
        boolean isLikedByCurrentUser = post.getLikes().stream()
                .anyMatch(like -> like.getUser().getId().equals(currentUserId));
        dto.setIsLikedByCurrentUser(isLikedByCurrentUser);

        boolean isSavedByCurrentUser = post.getSave().stream()
                .anyMatch(save -> save.getUser().getId().equals(currentUserId));
        dto.setIsSavedByCurrentUser(isSavedByCurrentUser);

        List<MCommentResponse> commentResponses = post.getComments().stream()
                .map(this::toCommentResponse)
                .toList();
        dto.setComments(commentResponses);

        return dto;
    }

    public List<MGetPostResponse> toPostResponses(List<Post> posts, String currentUserId) {
        return posts.stream()
                .map(post -> toPostResponse(post, currentUserId))
                .collect(Collectors.toList());
    }

    private MCommentResponse toCommentResponse(Comment comment) {
        MCommentResponse response = new MCommentResponse();
        response.setCommentId(comment.getId());
        response.setMessage(comment.getMessage());
        response.setCreatedAt(comment.getCreatedAt());
        response.setUsername(comment.getUser().getUsername());
        response.setProfilePictureUrl(comment.getUser().getProfilePictureUrl());
        return response;
    }
}
