package com.clone.instagram.backend.business;

import ch.qos.logback.core.util.StringUtil;
import com.clone.instagram.backend.entity.Comment;
import com.clone.instagram.backend.entity.Post;
import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.exception.CommentException;
import com.clone.instagram.backend.exception.PostException;
import com.clone.instagram.backend.exception.UserException;
import com.clone.instagram.backend.model.MCommentRequest;
import com.clone.instagram.backend.model.MCommentResponse;
import com.clone.instagram.backend.service.CommentService;
import com.clone.instagram.backend.service.PostService;
import com.clone.instagram.backend.service.UserService;
import com.clone.instagram.backend.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentBusiness {
    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;

    public CommentBusiness(CommentService commentService, UserService userService, PostService postService) {
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
    }

    public MCommentResponse createComment(MCommentRequest request) throws BaseException {
        Optional<String> optUserId = SecurityUtil.getCurrentUserId();
        if (optUserId.isEmpty()) {
            throw UserException.unauthorized();
        }
        String userId = optUserId.get();

        if (StringUtil.isNullOrEmpty(request.getPostId())) {
            throw CommentException.postIdNull();
        }

        if (StringUtil.isNullOrEmpty(request.getMessage())) {
            throw CommentException.messageNull();
        }

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw UserException.userNotFound();
        }
        User user = optUser.get();

        Optional<Post> optPost = postService.findById(request.getPostId());
        if (optPost.isEmpty()) {
            throw PostException.postNotFound();
        }
        Post post = optPost.get();


        Comment comment = commentService.create(post, user, request.getMessage(), new Date());


        MCommentResponse response = new MCommentResponse();
        response.setCommentId(comment.getId());
        response.setMessage(comment.getMessage());
        response.setCreatedAt(comment.getCreatedAt());
        response.setUsername(user.getUsername());
        response.setProfilePictureUrl(user.getProfilePictureUrl());

        return response;

    }

    public MCommentResponse updateComment(String id, MCommentRequest request) throws BaseException {

        if (StringUtil.isNullOrEmpty(id)) {
            throw CommentException.commentIdNull();
        }

        Optional<String> optUserId = SecurityUtil.getCurrentUserId();
        if (optUserId.isEmpty()) {
            throw UserException.unauthorized();
        }
        String userId = optUserId.get();

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw UserException.userNotFound();
        }
        User user = optUser.get();

        Optional<Comment> optComment = commentService.findById(id);
        if(optComment.isEmpty()){
            throw CommentException.commentNotFound();
        }
        Comment  comment = optComment.get();

        if(request.getMessage() != null){
            comment.setMessage(request.getMessage());
        }

        Comment updatedComment = commentService.update(comment);

        MCommentResponse response = new MCommentResponse();
        response.setCommentId(updatedComment.getId());
        response.setMessage(updatedComment.getMessage());
        response.setCreatedAt(updatedComment.getCreatedAt());
        response.setUsername(user.getUsername());
        response.setProfilePictureUrl(user.getProfilePictureUrl());

        return response;

    }

    public void deleteComment(String id) throws BaseException {
        Optional<Comment> opt = commentService.findById(id);
        if (opt.isEmpty()) {
            throw CommentException.commentNotFound();
        }
        commentService.deleteById(id);
    }
}
