package com.clone.instagram.backend.api;

import com.clone.instagram.backend.business.LikeBusiness;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.model.MLikeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/liked")
public class LikeApi {
    private final LikeBusiness business;

    public LikeApi(LikeBusiness business) {
        this.business = business;
    }

    @PostMapping("/{id}")
    public ResponseEntity<MLikeResponse> likePost(@PathVariable String id) throws BaseException {
        MLikeResponse response = business.likePost(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MLikeResponse> unLikePost(@PathVariable String id) throws BaseException {
        MLikeResponse response = business.likePost(id);
        return ResponseEntity.ok(response);
    }
}
