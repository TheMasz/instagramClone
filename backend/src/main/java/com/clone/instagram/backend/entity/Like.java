package com.clone.instagram.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_like_post")
public class Like extends BaseEntity  {

    @ManyToOne
    @JoinColumn(name = "m_post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "m_user_id", nullable = false)
    private User user;
}
