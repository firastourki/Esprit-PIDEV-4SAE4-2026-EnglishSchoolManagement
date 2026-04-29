
package com.englishschool.assessmentservice.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.englishschool.assessmentservice.entity.Assessment;
import com.englishschool.assessmentservice.repository.AssessmentRepository;

/**
 * Service layer for Assessment
 */
@Service
public class AssessmentService {
    private final AssessmentRepository repository;

    public AssessmentService(AssessmentRepository repository) {
        this.repository = repository;
    }

    public Assessment save(Assessment entity) {
        return repository.save(entity);
    }

    public List<Assessment> findAll() {
        return repository.findAll();
    }

    public Assessment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
