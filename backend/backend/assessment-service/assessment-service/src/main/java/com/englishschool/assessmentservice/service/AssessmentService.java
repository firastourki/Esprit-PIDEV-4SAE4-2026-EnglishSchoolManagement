package com.englishschool.assessmentservice.service;

import com.englishschool.assessmentservice.entity.Assessment;
import com.englishschool.assessmentservice.repository.AssessmentRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssessmentService {

    private final AssessmentRepository repository;

    public AssessmentService(AssessmentRepository repository) {
        this.repository = repository;
    }

    public Assessment create(Assessment assessment) {
        return repository.save(assessment);
    }

    public Page<Assessment> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return repository.findAll(pageable);
    }

    public Optional<Assessment> getById(Long id) {
        return repository.findById(id);
    }

    public Assessment update(Long id, Assessment updated) {
        Assessment existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        existing.setTitle(updated.getTitle());
        existing.setCourseName(updated.getCourseName());
        existing.setType(updated.getType());
        existing.setStatus(updated.getStatus());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Assessment> filterByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByTitleContainingIgnoreCase(title, pageable);
    }
}
