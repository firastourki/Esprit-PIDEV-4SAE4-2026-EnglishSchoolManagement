
package com.englishschool.assessmentservice.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.englishschool.assessmentservice.entity.Grade;
import com.englishschool.assessmentservice.service.GradeService;

/**
 * REST Controller for Grade
 */
@RestController
@RequestMapping("/api/grades")
@CrossOrigin
@Tag(name = "Grade", description = "Grade CRUD APIs")
public class GradeController {
    private final GradeService service;

    public GradeController(GradeService service) {
        this.service = service;
    }

    @PostMapping
    public Grade create(@RequestBody Grade entity) {
        return service.save(entity);
    }

    @GetMapping
    public List<Grade> getAll() {
        return service.findAll();
    }

    @GetMapping("/<built-in function id>")
    public Grade getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/<built-in function id>")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
