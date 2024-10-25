package com.clone.instagram.backend.business;

import com.clone.instagram.backend.entity.Post;
import com.clone.instagram.backend.entity.Save;
import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.exception.PostException;
import com.clone.instagram.backend.exception.UserException;
import com.clone.instagram.backend.mapper.PostMapper;
import com.clone.instagram.backend.model.MGetPostResponse;
import com.clone.instagram.backend.model.MSaveResponse;
import com.clone.instagram.backend.service.PostService;
import com.clone.instagram.backend.service.SaveService;
import com.clone.instagram.backend.service.UserService;
import com.clone.instagram.backend.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaveBusiness {

    private final PostService postService;
    private final UserService userService;
    private final SaveService saveService;
    private final PostMapper postMapper;

    public SaveBusiness(PostService postService, UserService userService, SaveService saveService, PostMapper postMapper) {
        this.postService = postService;
        this.userService = userService;
        this.saveService = saveService;
        this.postMapper = postMapper;
    }

    public MSaveResponse savePost(String id) throws BaseException {

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
        Optional<User> optUser = userService.findById(userId);
        User user = optUser.get();

        Optional<Save> existingSave = saveService.findSaveByUserAndPost(user, post);

        if (existingSave.isPresent()) {
            saveService.unsave(existingSave.get());
            MSaveResponse response = new MSaveResponse();
            response.setIsSavedByCurrentUser(false);
            response.setMessage("UnSaved successfully.");
            return response;
        } else {
            Save newSave = new Save();
            newSave.setPost(post);
            newSave.setUser(optUser.get());
            saveService.save(newSave);
            MSaveResponse response = new MSaveResponse();
            response.setIsSavedByCurrentUser(true);
            response.setMessage("Saved successfully.");
            return response;
        }
    }

    public List<MGetPostResponse> getSavedPost() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if(opt.isEmpty()){
            throw UserException.unauthorized();
        }
        String userId = opt.get();

        List<Save> savedPosts  = saveService.findSavedByUserId(userId);

        if(savedPosts .isEmpty()){
            // throw no saved
        }

        List<Post> posts = savedPosts.stream()
                .map(Save::getPost)
                .collect(Collectors.<Post>toList());

        return postMapper.toPostResponses(posts, userId);
    }


}
