package com.clone.instagram.backend.repository;

import com.clone.instagram.backend.entity.Like;
import com.clone.instagram.backend.entity.Post;
import com.clone.instagram.backend.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface LikeRepository extends CrudRepository<Like, String> {

    Optional<Like> findByUserAndPost(User user, Post post);
}

