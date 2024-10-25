package com.clone.instagram.backend.repository;

import com.clone.instagram.backend.entity.Follower;
import com.clone.instagram.backend.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FollowerRepository extends CrudRepository<Follower, String> {
    Optional<Follower> findByFollowerAndFollowing(User follower, User following);

    List<Follower> findByFollowerId(String followerId);
}
