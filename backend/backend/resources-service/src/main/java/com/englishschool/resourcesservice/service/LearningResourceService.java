package com.englishschool.resourcesservice.service;

import com.englishschool.resourcesservice.entity.LearningResource;
import com.englishschool.resourcesservice.repository.LearningResourceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LearningResourceService {

    private final LearningResourceRepository repository;

    public LearningResourceService(LearningResourceRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public LearningResource save(LearningResource resource) {
        return repository.save(resource);
    }

    // GET ALL
    public Page<LearningResource> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // GET BY ID
    public LearningResource findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    // UPDATE
    public LearningResource update(Long id, LearningResource resource) {

        LearningResource existing = findById(id);

        existing.setTitle(resource.getTitle());
        existing.setType(resource.getType());
        existing.setLevel(resource.getLevel());
        existing.setPublished(resource.isPublished());
        existing.setFileUrl(resource.getFileUrl()); // ✅ CORRECT FIELD

        return repository.save(existing);
    }

    // DELETE
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // SEARCH
    public Page<LearningResource> search(String title, Pageable pageable) {
        return repository.findByTitleContainingIgnoreCase(title, pageable);
    }

    // FILTER BY TYPE
    public Page<LearningResource> filterByType(String type, Pageable pageable) {
        return repository.findByType(type, pageable);
    }

    // FILTER BY PUBLISHED
    public Page<LearningResource> filterByPublished(boolean published, Pageable pageable) {
        return repository.findByPublished(published, pageable);
    }
}
