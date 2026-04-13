package com.esprit.esmauthms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.storage.avatar-dir:avatars}")
    private String avatarDir;

    public String storeAvatar(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Empty file");
        }

        try {
            String extension = "";
            String original = file.getOriginalFilename();
            if (original != null && original.contains(".")) {
                extension = original.substring(original.lastIndexOf("."));
            }

            String fileName = UUID.randomUUID().toString() + extension;

            Path uploadDir = Paths.get(avatarDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadDir);

            Path target = uploadDir.resolve(fileName);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            // In a real setup you might serve these via static resources, here we just return the path string
            return "/files/avatars/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store file", e);
        }
    }
}
