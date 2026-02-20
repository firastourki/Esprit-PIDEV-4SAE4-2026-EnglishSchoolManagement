package com.englishschool.assessmentservice.controller;

import com.englishschool.assessmentservice.entity.Assessment;
import com.englishschool.assessmentservice.entity.AssessmentStatus;
import com.englishschool.assessmentservice.entity.AssessmentType;
import com.englishschool.assessmentservice.service.AssessmentService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assessments")
@CrossOrigin(origins = "http://localhost:4200")
public class AssessmentController {

    private final AssessmentService service;

    public AssessmentController(AssessmentService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public Assessment create(@Valid @RequestBody Assessment assessment) {
        return service.create(assessment);
    }

    // READ ALL (pagination)
    @GetMapping
    public Page<Assessment> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.getAll(page, size);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Assessment getById(@PathVariable Long id) {
        return service.getById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));
    }

    // UPDATE
    @PutMapping("/{id}")
    public Assessment update(@PathVariable Long id,
                             @Valid @RequestBody Assessment assessment) {
        return service.update(id, assessment);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // FILTER
    @GetMapping("/search")
    public Page<Assessment> search(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.filterByTitle(title, page, size);
    }


    @GetMapping("/types")
    public AssessmentType[] getTypes() {
        return AssessmentType.values();
    }

    @GetMapping("/statuses")
    public AssessmentStatus[] getStatuses() {
        return AssessmentStatus.values();
    }
}
