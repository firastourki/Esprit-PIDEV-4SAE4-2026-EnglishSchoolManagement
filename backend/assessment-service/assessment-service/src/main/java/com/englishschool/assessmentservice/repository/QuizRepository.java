package com.englishschool.assessmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.englishschool.assessmentservice.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
