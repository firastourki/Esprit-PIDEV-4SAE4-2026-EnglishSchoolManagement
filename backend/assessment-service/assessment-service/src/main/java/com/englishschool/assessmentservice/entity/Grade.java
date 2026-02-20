
package com.englishschool.assessmentservice.entity;

import jakarta.persistence.*;

/**
 * Entity class for Grade
 */
@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private Double score;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
