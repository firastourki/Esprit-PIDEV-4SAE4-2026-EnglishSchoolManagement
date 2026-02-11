
package com.englishschool.resourcesservice.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.englishschool.resourcesservice.entity.LearningResource;
import com.englishschool.resourcesservice.repository.LearningResourceRepository;

/**
 * Service layer for LearningResource
 */
@Service
public class LearningResourceService {
    private final LearningResourceRepository repository;

    public LearningResourceService(LearningResourceRepository repository) {
        this.repository = repository;
    }

    public LearningResource save(LearningResource entity) {
        return repository.save(entity);
    }

    public List<LearningResource> findAll() {
        return repository.findAll();
    }

    public LearningResource findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("LearningResource not found"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
