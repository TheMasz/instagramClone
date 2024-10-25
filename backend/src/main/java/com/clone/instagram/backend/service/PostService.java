package com.clone.instagram.backend.service;

import com.clone.instagram.backend.entity.Post;
import com.clone.instagram.backend.entity.PostImage;
import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.repository.LikeRepository;
import com.clone.instagram.backend.repository.PostImageRepository;
import com.clone.instagram.backend.repository.PostRepository;
import com.clone.instagram.backend.repository.SaveRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PostService {

    private final PostRepository repository;
    private final FileStorageService fileStorageService;
    private final PostImageRepository postImageRepository;


    public PostService(PostRepository repository, FileStorageService fileStorageService, PostImageRepository postImageRepository, LikeRepository likeRepository, SaveRepository saveRepository) {
        this.repository = repository;
        this.fileStorageService = fileStorageService;
        this.postImageRepository = postImageRepository;

    }

    public List<Post> findPostsByUser(User user) throws BaseException {
        List<Post> posts = repository.findByUserIdIn(Collections.singletonList(user.getId()));

        posts.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));

        return posts;
    }


    public List<Post> findAllPostsByFollowedUsers(User user) throws BaseException {

        List<String> followedUserIds = user.getFollowers().stream().map(follower -> follower.getFollowing().getId()).collect(Collectors.toList());

        followedUserIds.add(user.getId());

        List<Post> posts = repository.findByUserIdIn(followedUserIds);

        posts.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));

        return posts;
    }


    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        repository.findAll().forEach(posts::add);
        posts.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));

        return posts;
    }

    public Post create(List<MultipartFile> files, String desc, Date createdAt, User user) throws BaseException, IOException {

        Post post = new Post();
        post.setDescription(desc);
        post.setCreatedAt(createdAt);
        post.setUser(user);

        Post savedPost = repository.save(post);

        List<PostImage> postImages = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null && !originalFilename.isEmpty()) {
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

                String uniqueFileName = UUID.randomUUID().toString()
                        + "_"
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                        + fileExtension;

                fileStorageService.savePostImage(file, savedPost.getId(), uniqueFileName);

                PostImage postImage = new PostImage();
                postImage.setImageUrl("/posts/" + savedPost.getId() + "/" + uniqueFileName);
                postImage.setPost(savedPost);

                postImages.add(postImage);
            }
        }

        postImageRepository.saveAll(postImages);

        savedPost.setPostImages(postImages);
        return repository.save(savedPost);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public void deleteImageById(String id) {
        postImageRepository.deleteById(id);
    }

    public Optional<Post> findById(String id) {
        return repository.findById(id);
    }

    public Post update(Post post) {
        return repository.save(post);
    }


}
