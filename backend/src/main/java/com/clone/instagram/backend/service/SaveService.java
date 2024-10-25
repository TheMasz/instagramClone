package com.clone.instagram.backend.service;

import com.clone.instagram.backend.entity.Post;
import com.clone.instagram.backend.entity.Save;
import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.repository.SaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaveService {
    private final SaveRepository saveRepository;

    public SaveService(SaveRepository saveRepository) {
        this.saveRepository = saveRepository;
    }

    public Optional<Save> findSaveByUserAndPost(User user, Post post) {
        return saveRepository.findByUserAndPost(user, post);
    }

    public List<Save> findSavedByUserId(String userId){
        return saveRepository.findByUserId(userId);
    }

    public void unsave(Save save) {
        saveRepository.delete(save);
    }

    public void save(Save save) {
        saveRepository.save(save);
    }


}
