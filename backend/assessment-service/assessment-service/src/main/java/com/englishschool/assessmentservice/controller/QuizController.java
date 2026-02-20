package com.englishschool.assessmentservice.controller;

import com.englishschool.assessmentservice.entity.Quiz;
import com.englishschool.assessmentservice.entity.QuizAttempt;
import com.englishschool.assessmentservice.service.QuizService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // ===============================
    // CREATE QUIZ
    // ===============================
    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.createQuiz(quiz);
    }

    // ===============================
    // START QUIZ
    // ===============================
    @PostMapping("/{quizId}/start/{studentId}")
    public QuizAttempt startQuiz(@PathVariable Long quizId,
                                 @PathVariable Long studentId) {
        return quizService.startQuiz(quizId, studentId);
    }

    // ===============================
    // SUBMIT ANSWER
    // ===============================
    @PostMapping("/{quizId}/submit/{studentId}")
    public QuizAttempt submitAnswer(@PathVariable Long quizId,
                                    @PathVariable Long studentId,
                                    @RequestParam Long optionId) {
        return quizService.submitAnswer(quizId, studentId, optionId);
    }

    // ===============================
    // GET RESULT
    // ===============================
    @GetMapping("/{quizId}/result/{studentId}")
    public QuizAttempt getResult(@PathVariable Long quizId,
                                 @PathVariable Long studentId) {
        return quizService.getResult(quizId, studentId);
    }
}
