package com.clone.instagram.backend.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MUpdatePostRequest {
    private String description;
//    private List<MultipartFile> files;
}
