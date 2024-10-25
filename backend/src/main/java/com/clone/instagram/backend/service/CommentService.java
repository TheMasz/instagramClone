package com.clone.instagram.backend.service;

import com.clone.instagram.backend.entity.Comment;
import com.clone.instagram.backend.entity.Post;
import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Optional<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    public Comment create(Post post, User user, String message, Date createdAt) {
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setMessage(message);
        comment.setCreatedAt(createdAt);

        return commentRepository.save(comment);
    }

    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }
}
