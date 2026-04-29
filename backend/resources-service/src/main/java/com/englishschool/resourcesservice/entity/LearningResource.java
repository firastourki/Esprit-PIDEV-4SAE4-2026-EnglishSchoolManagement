
package com.englishschool.resourcesservice.entity;

import jakarta.persistence.*;

/**
 * Entity class for LearningResource
 */
@Entity
public class LearningResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String type;
    private String url;
    private String level;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
