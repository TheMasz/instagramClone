package com.clone.instagram.backend.service;

import com.clone.instagram.backend.entity.Follower;
import com.clone.instagram.backend.entity.Like;
import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.repository.FollowerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {
    private final FollowerRepository repository;

    public FollowerService(FollowerRepository repository) {
        this.repository = repository;
    }

    public Optional<Follower> findByFollowerAndFollowing(User follower, User following) {
        return repository.findByFollowerAndFollowing(follower, following);
    }

    public void unfollow(Follower follower) {
        repository.delete(follower);
    }

    public void follow(Follower follower) {
        repository.save(follower);
    }

    public List<Follower> getFollowingsByUserId(String userId) {
        return repository.findByFollowerId(userId);
    }
}
