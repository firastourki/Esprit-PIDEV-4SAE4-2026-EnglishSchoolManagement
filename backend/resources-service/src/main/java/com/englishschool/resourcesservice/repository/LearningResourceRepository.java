
package com.englishschool.resourcesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.englishschool.resourcesservice.entity.LearningResource;

/**
 * Repository for LearningResource
 */
public interface LearningResourceRepository extends JpaRepository<LearningResource, Long> {
}
