package com.clone.instagram.backend.service;

import com.clone.instagram.backend.entity.Post;
import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.exception.BaseException;
import com.clone.instagram.backend.exception.UserException;
import com.clone.instagram.backend.mapper.UserMapper;
import com.clone.instagram.backend.model.MUserProfileResponse;
import com.clone.instagram.backend.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User create(String email, String username, String password, String token, Date tokenDateExpire) throws BaseException {

        if (Objects.isNull(email)) {
            throw UserException.emailNull();
        }
        if (Objects.isNull(username)) {
            throw UserException.usernameNull();
        }
        if (Objects.isNull(password)) {
            throw UserException.passwordNull();
        }
        if (repository.existsByEmail(email)) {
            throw UserException.createEmailDuplicate();
        }
        if (repository.existsByUsername(username)) {
            throw UserException.createUsernameDuplicate();
        }

        User entity = new User();
        entity.setEmail(email);
        entity.setUsername(username);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setToken(token);
        entity.setTokenExpire(tokenDateExpire);

        return repository.save(entity);
    }

    public User update(User user){
        return repository.save(user);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public Optional<User> findById(String id){
        return repository.findById(id);
    }

    public Optional<User> findByToken(String token) {
        return repository.findByToken(token);
    }

    public Optional<User> findByResetToken(String resetToken){
        return repository.findByResetToken(resetToken);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        repository.findAll().forEach(users::add);
        return users;
    }

}
