
package com.englishschool.assessmentservice.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.englishschool.assessmentservice.entity.Assessment;
import com.englishschool.assessmentservice.service.AssessmentService;

/**
 * REST Controller for Assessment
 */
@RestController
@RequestMapping("/api/assessments")
@CrossOrigin
@Tag(name = "Assessment", description = "Assessment CRUD APIs")
public class AssessmentController {
    private final AssessmentService service;

    public AssessmentController(AssessmentService service) {
        this.service = service;
    }

    @PostMapping
    public Assessment create(@RequestBody Assessment entity) {
        return service.save(entity);
    }

    @GetMapping
    public List<Assessment> getAll() {
        return service.findAll();
    }

    @GetMapping("/<built-in function id>")
    public Assessment getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/<built-in function id>")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
