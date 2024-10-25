package com.clone.instagram.backend.repository;

import com.clone.instagram.backend.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, String> {

}
