package com.englishschool.assessmentservice.service;

import com.englishschool.assessmentservice.entity.*;
import com.englishschool.assessmentservice.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;
    private final OptionRepository optionRepository;
    private final QuizAttemptRepository quizAttemptRepository;

    public QuizService(QuizRepository quizRepository,
                       OptionRepository optionRepository,
                       QuizAttemptRepository quizAttemptRepository) {
        this.quizRepository = quizRepository;
        this.optionRepository = optionRepository;
        this.quizAttemptRepository = quizAttemptRepository;
    }

    // =====================================
    // CREATE QUIZ
    // =====================================
    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    // =====================================
    // START QUIZ
    // =====================================
    public QuizAttempt startQuiz(Long quizId, Long studentId) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        LocalDateTime now = LocalDateTime.now();

        // Vérifier horaire global du quiz
        if (now.isBefore(quiz.getStartTime()) || now.isAfter(quiz.getEndTime())) {
            throw new RuntimeException("Quiz not available at this time");
        }

        // Vérifier tentative unique
        Optional<QuizAttempt> existing =
                quizAttemptRepository.findByQuizIdAndStudentId(quizId, studentId);

        if (existing.isPresent()) {
            throw new RuntimeException("Student already attempted this quiz");
        }

        QuizAttempt attempt = new QuizAttempt();
        attempt.setQuiz(quiz);
        attempt.setStudentId(studentId);
        attempt.setStartTime(now);
        attempt.setCompleted(false);
        attempt.setScore(0);

        return quizAttemptRepository.save(attempt);
    }

    // =====================================
    // SUBMIT ANSWER
    // =====================================
    public QuizAttempt submitAnswer(Long quizId,
                                    Long studentId,
                                    Long selectedOptionId) {

        QuizAttempt attempt =
                quizAttemptRepository.findByQuizIdAndStudentId(quizId, studentId)
                        .orElseThrow(() -> new RuntimeException("Attempt not found"));

        if (attempt.isCompleted()) {
            throw new RuntimeException("Quiz already submitted");
        }

        Quiz quiz = attempt.getQuiz();
        LocalDateTime now = LocalDateTime.now();

        // Vérifier durée individuelle
        if (now.isAfter(attempt.getStartTime()
                .plusMinutes(quiz.getDurationMinutes()))) {
            throw new RuntimeException("Time is over");
        }

        // Vérifier que le quiz n'est pas globalement expiré
        if (now.isAfter(quiz.getEndTime())) {
            throw new RuntimeException("Quiz session expired");
        }

        Option selectedOption =
                optionRepository.findById(selectedOptionId)
                        .orElseThrow(() -> new RuntimeException("Option not found"));

        // Vérifier que l'option appartient bien à la question du quiz
        if (!selectedOption.getQuestion().getQuiz().getId().equals(quizId)) {
            throw new RuntimeException("Invalid option for this quiz");
        }

        int score = selectedOption.isCorrect() ? 100 : 0;

        attempt.setScore(score);
        attempt.setCompleted(true);
        attempt.setSubmissionTime(now);

        return quizAttemptRepository.save(attempt);
    }

    // =====================================
    // GET RESULT
    // =====================================
    public QuizAttempt getResult(Long quizId, Long studentId) {

        return quizAttemptRepository
                .findByQuizIdAndStudentId(quizId, studentId)
                .orElseThrow(() ->
                        new RuntimeException("Attempt not found"));
    }
}
