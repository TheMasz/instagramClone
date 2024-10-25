package com.clone.instagram.backend.repository;

import com.clone.instagram.backend.entity.User;
import com.clone.instagram.backend.model.MUserProfileResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByToken(String token);

    Optional<User> findByResetToken(String resetToken);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);




}
