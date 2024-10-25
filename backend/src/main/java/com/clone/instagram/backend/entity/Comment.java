package com.clone.instagram.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_comment")
public class Comment extends BaseEntity  {

    private String message;
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "m_user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "m_post_id", nullable = false)
    private Post post;
}

