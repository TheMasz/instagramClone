package com.clone.instagram.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.List;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_user")
public class User extends BaseEntity implements Serializable {

    @Column(nullable = false, unique = true, length = 40)
    private String email;

    @Column(nullable = false, unique = true, length = 40)
    private String username;

    @JsonIgnore
    @Column(nullable = false, length = 120)
    private String password;

    @Column(length = 200)
    private String bio;

    private String profilePictureUrl;
    private String token;
    private Date tokenExpire;
    private boolean activated;
    private String resetToken;
    private Date resetTokenExpire;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Follower> followers;

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Follower> followings;


}
