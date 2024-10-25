package com.clone.instagram.common;

import lombok.Data;

@Data
public class EmailRequest {
    private String to;
    private String content;
    private String subject;
}
