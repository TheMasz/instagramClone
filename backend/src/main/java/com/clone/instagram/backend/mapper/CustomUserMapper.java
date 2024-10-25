package com.clone.instagram.backend.mapper;

import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.model.MUserProfileResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class CustomUserMapper {

    public List<MUserProfileResponse> toUsersProfile(List<User> users, String currentUserId) {
        return users.stream()
                .map(user -> toUserProfile(user, currentUserId))
                .collect(Collectors.toList());
    }

    public MUserProfileResponse toUserProfile(User user, String currentUserId) {
        MUserProfileResponse dto = new MUserProfileResponse();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setBio(user.getBio());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());

        dto.setFollowerCount(user.getFollowings().size());
        dto.setFollowingCount(user.getFollowers().size());
        boolean isFollowedByCurrentUser = user.getFollowings().stream()
                .anyMatch(follower -> follower.getFollower().getId().equals(currentUserId));
        dto.setFollowedByCurrentUser(isFollowedByCurrentUser);

        return dto;
    }
}
