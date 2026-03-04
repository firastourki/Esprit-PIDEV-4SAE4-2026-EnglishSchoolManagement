package com.englishschool.assessmentservice.repository;

import com.englishschool.assessmentservice.entity.Assessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    Page<Assessment> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Assessment> findByCourseNameContainingIgnoreCase(String courseName, Pageable pageable);
}
