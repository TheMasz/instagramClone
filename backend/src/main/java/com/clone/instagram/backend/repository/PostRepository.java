package com.clone.instagram.backend.repository;

import com.clone.instagram.backend.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostRepository extends CrudRepository<Post, String> {
    List<Post> findByUserIdIn(List<String> userIds);
}

