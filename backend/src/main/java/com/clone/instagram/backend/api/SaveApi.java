package com.clone.instagram.backend.api;

import com.clone.instagram.backend.business.SaveBusiness;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.model.MGetPostResponse;
import com.clone.instagram.backend.model.MSaveResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saved")
public class SaveApi {
    private final SaveBusiness business;

    public SaveApi(SaveBusiness business) {
        this.business = business;
    }

    @GetMapping("")
    public ResponseEntity<List<MGetPostResponse>> getSavedPost() throws BaseException {
        List<MGetPostResponse> response = business.getSavedPost();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/save")
    public ResponseEntity<MSaveResponse> savePost(@PathVariable String id) throws BaseException {
        MSaveResponse response = business.savePost(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/unsave")
    public ResponseEntity<MSaveResponse> unsavePost(@PathVariable String id) throws BaseException {
        MSaveResponse response = business.savePost(id);
        return ResponseEntity.ok(response);
    }
}
