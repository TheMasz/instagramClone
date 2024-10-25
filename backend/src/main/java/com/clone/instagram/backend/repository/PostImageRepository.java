package com.clone.instagram.backend.repository;

import com.clone.instagram.backend.entity.PostImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface PostImageRepository extends CrudRepository<PostImage , String> {
}
