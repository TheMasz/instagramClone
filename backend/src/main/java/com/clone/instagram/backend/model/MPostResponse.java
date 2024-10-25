package com.clone.instagram.backend.model;

import lombok.Data;

import java.util.Date;

@Data
public class MPostResponse {
    private String description;
    private String[] images;
    private Date createdAt;
}
