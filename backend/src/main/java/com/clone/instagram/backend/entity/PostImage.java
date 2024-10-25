package com.clone.instagram.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_post_image")
public class PostImage extends BaseEntity implements Serializable {

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "m_post_id", nullable = false)
    private Post post;
}
