package com.clone.instagram.backend.model;

import lombok.Data;

@Data
public class MResendResetPasswordRequest {
    private String resetToken;
}
