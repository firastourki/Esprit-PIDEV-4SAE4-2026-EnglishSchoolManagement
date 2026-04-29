
package com.englishschool.assessmentservice.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.englishschool.assessmentservice.entity.Grade;
import com.englishschool.assessmentservice.repository.GradeRepository;

/**
 * Service layer for Grade
 */
@Service
public class GradeService {
    private final GradeRepository repository;

    public GradeService(GradeRepository repository) {
        this.repository = repository;
    }

    public Grade save(Grade entity) {
        return repository.save(entity);
    }

    public List<Grade> findAll() {
        return repository.findAll();
    }

    public Grade findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
