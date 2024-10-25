package com.clone.instagram.backend.repository;

import com.clone.instagram.backend.entity.Like;
import com.clone.instagram.backend.entity.Post;
import com.clone.instagram.backend.entity.Save;
import com.clone.instagram.backend.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SaveRepository extends CrudRepository<Save, String> {

    List<Save> findByUserId(String userId);

    Optional<Save> findByUserAndPost(User user, Post post);

}
