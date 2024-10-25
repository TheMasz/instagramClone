package com.clone.instagram.backend.model;

import lombok.Data;

@Data
public class MResetPasswordRequest {
    private String resetToken;
    private String newPassword;
}
