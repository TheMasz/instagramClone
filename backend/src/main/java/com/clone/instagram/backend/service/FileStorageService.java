package com.clone.instagram.backend.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@Service
public class FileStorageService {

    @Value("${app.file.upload-dir}")
    private String baseUploadDir;

    @Value("${app.file.profile-pictures-dir}")
    private String profilePicturesDir;

    @Value("${app.file.posts-dir}")
    private String postsDir;

    public String saveProfilePicture(MultipartFile file, String userId, String fileExtension) throws IOException {
        String fileName = userId + fileExtension;
        Path uploadPath = Paths.get(baseUploadDir, profilePicturesDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path oldFilePath = uploadPath.resolve(fileName);
        if (Files.exists(oldFilePath)) {
            Files.delete(oldFilePath);
        }

        Path newFilePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), newFilePath);

        return profilePicturesDir + fileName;
    }

    public String savePostImage(MultipartFile file, String postId, String fileName) throws IOException {
        Path uploadPath = Paths.get(baseUploadDir, postsDir, postId);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path newFilePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), newFilePath);
        return postsDir + "/" + postId + "/" + fileName;
    }


    public void deleteFile(String filePath) throws IOException {
        String relativeFilePath = filePath.replaceFirst("^/uploads/", "");
        Path fileToDeletePath = Paths.get(baseUploadDir, relativeFilePath);

        if (Files.exists(fileToDeletePath)) {
            Files.delete(fileToDeletePath);
        } else {
            throw new IOException("File not found: " + filePath);
        }
    }

}
