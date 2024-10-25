package com.clone.instagram.backend.model;

import lombok.Data;

@Data
public class MSaveResponse {
    private String message;
    private Boolean isSavedByCurrentUser;
}
