package com.clone.instagram.backend.service;

import com.clone.instagram.backend.entity.Like;
import com.clone.instagram.backend.entity.Post;
import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Optional<Like> findLikeByUserAndPost(User user, Post post) {
        return likeRepository.findByUserAndPost(user, post);
    }

    public void unlike(Like like) {
        likeRepository.delete(like);
    }

    public void like(Like like) {
        likeRepository.save(like);
    }
}
