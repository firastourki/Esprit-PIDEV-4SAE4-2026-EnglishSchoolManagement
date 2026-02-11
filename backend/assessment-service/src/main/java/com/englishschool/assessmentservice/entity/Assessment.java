
package com.englishschool.assessmentservice.entity;

import jakarta.persistence.*;

/**
 * Entity class for Assessment
 */
@Entity
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String courseName;
    private String type;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
