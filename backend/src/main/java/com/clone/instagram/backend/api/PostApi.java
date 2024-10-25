package com.clone.instagram.backend.api;

import com.clone.instagram.backend.business.PostBusiness;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostApi {

    private final PostBusiness business;


    public PostApi(PostBusiness business) {
        this.business = business;
    }

    @GetMapping("/getByUser/{username}")
    public ResponseEntity<List<MGetPostResponse>> getPostByUser(@PathVariable String username) throws BaseException {
        List<MGetPostResponse> response = business.getPostByUser(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MGetPostResponse> getPost(@PathVariable String id) throws BaseException {
        MGetPostResponse response = business.getPost(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MGetPostResponse>> getPosts() throws BaseException {
        List<MGetPostResponse> response = business.getPostsByFollowed();
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<MCreatePostResponse> createPost(@ModelAttribute MCreatePostRequest request) throws BaseException, IOException {
        MCreatePostResponse response = business.createPost(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MPostResponse> updatePost(@PathVariable String id, @ModelAttribute MUpdatePostRequest request) throws BaseException, IOException {
        MPostResponse response = business.updatePost(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) throws BaseException {
        business.deletePost(id);
        return ResponseEntity.ok().build();
    }


}
