package com.clone.instagram.backend.model;

import lombok.Data;

import java.util.Date;

@Data
public class MCreatePostResponse {
    private String id;
    private String description;
    private String[] images;
    private Date createdAt;
}
