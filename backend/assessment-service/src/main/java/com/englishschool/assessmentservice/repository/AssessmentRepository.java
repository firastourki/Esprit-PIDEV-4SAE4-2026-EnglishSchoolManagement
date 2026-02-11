
package com.englishschool.assessmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.englishschool.assessmentservice.entity.Assessment;

/**
 * Repository for Assessment
 */
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}
