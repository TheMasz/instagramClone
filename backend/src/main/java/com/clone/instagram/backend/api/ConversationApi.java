package com.clone.instagram.backend.api;

import com.clone.instagram.backend.business.ConversationBusiness;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.model.MConversationResponse;
import com.clone.instagram.backend.model.MNewConversationRequest;
import com.clone.instagram.backend.model.MNewConversationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conversations")
public class ConversationApi {
    private final ConversationBusiness business;

    public ConversationApi(ConversationBusiness business) {
        this.business = business;
    }

//    @PostMapping("/message")
//    public ResponseEntity<Void> post(@RequestBody MChatMessageRequest request) throws BaseException {
//        business.post(request);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }

    @PostMapping("")
    public ResponseEntity<MNewConversationResponse> newConversation(@RequestBody MNewConversationRequest request) throws BaseException {
        MNewConversationResponse response = business.newConversation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MConversationResponse> getConversation(@PathVariable String id) throws BaseException {
        MConversationResponse response = business.getConversation(id);
        return ResponseEntity.ok(response);
    }

}
