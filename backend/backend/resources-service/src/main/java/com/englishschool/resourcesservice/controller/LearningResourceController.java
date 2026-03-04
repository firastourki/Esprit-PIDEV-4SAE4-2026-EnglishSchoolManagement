package com.englishschool.resourcesservice.controller;

import com.englishschool.resourcesservice.entity.LearningResource;
import com.englishschool.resourcesservice.service.LearningResourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin
public class LearningResourceController {

    private final LearningResourceService service;

    public LearningResourceController(LearningResourceService service) {
        this.service = service;
    }

    // ✅ CREATE AVEC UPLOAD PDF
    @PostMapping(consumes = "multipart/form-data")
    public LearningResource create(
            @RequestParam String title,
            @RequestParam String type,
            @RequestParam String courseName,   // ✅ AJOUT IMPORTANT
            @RequestParam String level,
            @RequestParam boolean published,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {

        LearningResource resource = new LearningResource();
        resource.setTitle(title);
        resource.setType(type);
        resource.setCourseName(courseName);   // ✅ AJOUT IMPORTANT
        resource.setLevel(level);
        resource.setPublished(published);

        if (file != null && !file.isEmpty()) {
            try {
                String uploadDir = "uploads/";
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);

                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                resource.setFileUrl(fileName);

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("File upload failed");
            }
        }

        return service.save(resource);
    }

    @GetMapping
    public Page<LearningResource> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @PutMapping("/{id}")
    public LearningResource update(@PathVariable Long id,
                                   @RequestBody LearningResource resource) {
        return service.update(id, resource);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

