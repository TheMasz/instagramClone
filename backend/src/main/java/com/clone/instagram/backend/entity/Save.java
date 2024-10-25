package com.clone.instagram.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_save_post")
public class Save extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "m_post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "m_user_id", nullable = false)
    private User user;
}
