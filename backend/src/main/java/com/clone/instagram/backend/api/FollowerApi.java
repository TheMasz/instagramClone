package com.clone.instagram.backend.api;

import com.clone.instagram.backend.business.FollowerBusiness;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.model.MFollowerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follower")
public class FollowerApi {
    private final FollowerBusiness business;

    public FollowerApi(FollowerBusiness business) {
        this.business = business;
    }

    @PostMapping("/{id}")
    public ResponseEntity<MFollowerResponse> follow(@PathVariable String id) throws BaseException {
        MFollowerResponse response = business.follow(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MFollowerResponse> unfollow(@PathVariable String id) throws BaseException {
        MFollowerResponse response = business.follow(id);
        return ResponseEntity.ok(response);
    }
}
