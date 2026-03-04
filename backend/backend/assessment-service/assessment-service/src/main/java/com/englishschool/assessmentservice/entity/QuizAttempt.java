package com.englishschool.assessmentservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"quiz_id", "studentId"})
)
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;

    private LocalDateTime startTime;
    private LocalDateTime submissionTime;

    private boolean completed;
    private int score;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "selected_option_id")
    private Option selectedOption;

    public QuizAttempt() {
    }

    // ================= GETTERS =================

    public Long getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getScore() {
        return score;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public Option getSelectedOption() {
        return selectedOption;
    }

    // ================= SETTERS =================

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setSelectedOption(Option selectedOption) {
        this.selectedOption = selectedOption;
    }
}
