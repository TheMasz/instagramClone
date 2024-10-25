package com.clone.instagram.backend.model;

import lombok.Data;

@Data
public class MNewPasswordRequest {
    private String oldPassword;
    private String newPassword;
}
