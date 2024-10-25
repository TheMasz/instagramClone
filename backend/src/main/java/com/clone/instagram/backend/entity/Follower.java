package com.clone.instagram.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_follower")
public class Follower extends BaseEntity  {

    @ManyToOne
    @JoinColumn(name = "follower_id") // The user who is following
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id") // The user being followed
    private User following;

    private Date createdAt;
}
