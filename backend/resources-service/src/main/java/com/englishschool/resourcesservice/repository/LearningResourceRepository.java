package com.englishschool.resourcesservice.repository;

import com.englishschool.resourcesservice.entity.LearningResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningResourceRepository extends JpaRepository<LearningResource, Long> {

    Page<LearningResource> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<LearningResource> findByType(String type, Pageable pageable);

    Page<LearningResource> findByPublished(boolean published, Pageable pageable);
}
