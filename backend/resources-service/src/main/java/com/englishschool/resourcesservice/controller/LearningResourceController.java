
package com.englishschool.resourcesservice.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.englishschool.resourcesservice.entity.LearningResource;
import com.englishschool.resourcesservice.service.LearningResourceService;

/**
 * REST Controller for LearningResource
 */
@RestController
@RequestMapping("/api/learningresources")
@CrossOrigin
@Tag(name = "LearningResource", description = "LearningResource CRUD APIs")
public class LearningResourceController {
    private final LearningResourceService service;

    public LearningResourceController(LearningResourceService service) {
        this.service = service;
    }

    @PostMapping
    public LearningResource create(@RequestBody LearningResource entity) {
        return service.save(entity);
    }

    @GetMapping
    public List<LearningResource> getAll() {
        return service.findAll();
    }

    @GetMapping("/<built-in function id>")
    public LearningResource getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/<built-in function id>")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
