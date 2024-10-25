package com.clone.instagram.backend.business;

import com.clone.instagram.backend.entity.Like;
import com.clone.instagram.backend.entity.Post;
import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.exception.PostException;
import com.clone.instagram.backend.exception.UserException;
import com.clone.instagram.backend.model.MLikeResponse;
import com.clone.instagram.backend.service.LikeService;
import com.clone.instagram.backend.service.PostService;
import com.clone.instagram.backend.service.UserService;
import com.clone.instagram.backend.util.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class LikeBusiness {

    private final PostService postService;
    private final UserService userService;
    private final LikeService likeService;

    public LikeBusiness(PostService postService, UserService userService, LikeService likeService) {
        this.postService = postService;
        this.userService = userService;
        this.likeService = likeService;
    }

    public MLikeResponse likePost(String id) throws BaseException {

        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<Post> optPost = postService.findById(id);
        if (optPost.isEmpty()) {
            throw PostException.postNotFound();
        }
        Post post = optPost.get();

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw UserException.userNotFound();
        }
        User user = optUser.get();

        Optional<Like> existingLike = likeService.findLikeByUserAndPost(user, post);

        if (existingLike.isPresent()) {
            likeService.unlike(existingLike.get());
            MLikeResponse response = new MLikeResponse();
            response.setIsLikedByCurrentUser(false);
            response.setMessage("Unliked successfully.");
            response.setLikeCount(post.getLikes().size());
            return response;
        } else {
            Like newLike = new Like();
            newLike.setPost(post);
            newLike.setUser(optUser.get());
            likeService.like(newLike);
            MLikeResponse response = new MLikeResponse();
            response.setIsLikedByCurrentUser(true);
            response.setMessage("Liked successfully.");
            response.setLikeCount(post.getLikes().size());
            return response;
        }
    }
}
