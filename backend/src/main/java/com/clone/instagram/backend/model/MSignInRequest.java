package com.clone.instagram.backend.model;

import lombok.Data;

@Data
public class MSignInRequest {
    private String emailOrUsername;
    private String password;
}
