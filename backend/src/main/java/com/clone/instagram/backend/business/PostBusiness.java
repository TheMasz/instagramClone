package com.clone.instagram.backend.business;

import ch.qos.logback.core.util.StringUtil;
import com.clone.instagram.backend.entity.Post;
import com.clone.instagram.backend.entity.PostImage;
import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.exception.PostException;
import com.clone.instagram.backend.exception.UserException;
import com.clone.instagram.backend.mapper.PostMapper;
import com.clone.instagram.backend.model.*;
import com.clone.instagram.backend.service.FileStorageService;
import com.clone.instagram.backend.service.PostService;
import com.clone.instagram.backend.service.UserService;
import com.clone.instagram.backend.util.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Log4j2
@Service
public class PostBusiness {

    private final PostService postService;
    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final PostMapper postMapper;


    public PostBusiness(PostService postService, UserService userService, FileStorageService fileStorageService, PostMapper postMapper) {
        this.postService = postService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.postMapper = postMapper;
    }

    public List<MGetPostResponse> getPostByUser(String username) throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }
        String userId = opt.get();

        Optional<User> optUser = userService.findByUsername(username);
        if(optUser.isEmpty()){
            throw UserException.userNotFound();
        }
        User user = optUser.get();

        List<Post> postsByUser = postService.findPostsByUser(user);
        return postMapper.toPostResponses(postsByUser, userId);

    }

    public MGetPostResponse getPost(String id) throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();

        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<Post> optPost = postService.findById(id);
        if (optPost.isEmpty()) {
            throw PostException.postNotFound();
        }

        Post post = optPost.get();

        return postMapper.toPostResponse(post, userId);
    }

    public List<MGetPostResponse> getPostsByFollowed() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();

        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findById(userId);
        if(optUser.isEmpty()){
            throw UserException.userNotFound();
        }

        User user = optUser.get();

        List<Post> posts = postService.findAllPostsByFollowedUsers(user);
        return postMapper.toPostResponses(posts, userId);
    }

    public MCreatePostResponse createPost(MCreatePostRequest request) throws BaseException, IOException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();

        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();
        Optional<User> optUser = userService.findById(userId);

        if (optUser.isEmpty()) {
            throw UserException.userNotFound();
        }

        User user = optUser.get();
        if (request.getFiles() == null || request.getFiles().isEmpty()) {
            throw PostException.filesNull();
        }

        if (Objects.isNull(request.getDescription()) || request.getDescription().trim().isEmpty()) {
            throw PostException.descNull();
        }

        Post post = postService.create(
                request.getFiles(),
                request.getDescription(),
                new Date(),
                user
        );

        MCreatePostResponse response = new MCreatePostResponse();
        response.setId(post.getId());
        response.setCreatedAt(post.getCreatedAt());
        response.setDescription(post.getDescription());

        List<PostImage> postImages = post.getPostImages();

        String[] imageUrls = postImages.stream()
                .map(PostImage::getImageUrl)
                .toArray(String[]::new);

        response.setImages(imageUrls);

        return response;
    }

    public void deletePost(String id) throws BaseException {
        Optional<Post> opt = postService.findById(id);
        if (opt.isEmpty()) {
            throw PostException.postNotFound();
        }

        postService.deleteById(id);
    }

    @Transactional
    public MPostResponse updatePost(String id, MUpdatePostRequest request) throws BaseException, IOException {
        if (StringUtil.isNullOrEmpty(id)) {
            throw PostException.postIDNull();
        }

        Optional<Post> opt = postService.findById(id);
        if (opt.isEmpty()) {
            throw PostException.postNotFound();
        }

        Post post = opt.get();

        if (request.getDescription() != null) {
            post.setDescription(request.getDescription());
        }

//        **for update images**
//        if (request.getFiles() != null && !request.getFiles().isEmpty()) {
//            List<PostImage> oldPostImages = new ArrayList<>(post.getPostImages());
//
//            for (PostImage oldImage : oldPostImages) {
//                fileStorageService.deleteFile(oldImage.getImageUrl());
//                postService.deleteImageById(oldImage.getId());
//            }
//
//            post.getPostImages().clear();
//
//
//            for (MultipartFile file : request.getFiles()) {
//                String postId = post.getId();
//                String originalFilename = file.getOriginalFilename();
//
//                if (originalFilename != null && !originalFilename.isEmpty()) {
//                    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
//                    String uniqueFileName = UUID.randomUUID().toString() + "_" +
//                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + fileExtension;
//
//                    fileStorageService.savePostImage(file, postId, uniqueFileName);
//
//                    PostImage postImage = new PostImage();
//                    postImage.setImageUrl("/posts/" + postId + "/" + uniqueFileName);
//                    postImage.setPost(post);
//                    post.getPostImages().add(postImage);
//                }
//            }
//        }

        Post updatedPost = postService.update(post);

        MPostResponse response = new MPostResponse();
        response.setDescription(updatedPost.getDescription());
        response.setImages(updatedPost.getPostImages()
                .stream()
                .map(PostImage::getImageUrl)
                .toArray(String[]::new));
        response.setCreatedAt(updatedPost.getCreatedAt());

        return response;
    }


}
