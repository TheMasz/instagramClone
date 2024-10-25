package com.clone.instagram.backend.api;

import com.clone.instagram.backend.business.CommentBusiness;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.model.MCommentRequest;
import com.clone.instagram.backend.model.MCommentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentApi {

    private final CommentBusiness business;

    public CommentApi(CommentBusiness business) {
        this.business = business;
    }

    @PostMapping("")
    public ResponseEntity<MCommentResponse> createComment(@RequestBody MCommentRequest request) throws BaseException {
        MCommentResponse response = business.createComment(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MCommentResponse> updateComment(@PathVariable String id, @RequestBody MCommentRequest request) throws BaseException {
        MCommentResponse response = business.updateComment(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id ) throws BaseException {
        business.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
