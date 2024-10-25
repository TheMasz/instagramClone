package com.clone.instagram.backend.model;

import lombok.Data;

@Data
public class MSignUpRequest {
    private String email;
    private String username;
    private String password;
}
