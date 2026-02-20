package com.englishschool.assessmentservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "assessments")
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100)
    private String title;

    @NotBlank(message = "Course name is required")
    private String courseName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssessmentType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssessmentStatus status;

    public Assessment() {}

    public Assessment(String title, String courseName, AssessmentType type, AssessmentStatus status) {
        this.title = title;
        this.courseName = courseName;
        this.type = type;
        this.status = status;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getCourseName() { return courseName; }

    public void setCourseName(String courseName) { this.courseName = courseName; }

    public AssessmentType getType() { return type; }

    public void setType(AssessmentType type) { this.type = type; }

    public AssessmentStatus getStatus() { return status; }

    public void setStatus(AssessmentStatus status) { this.status = status; }
}