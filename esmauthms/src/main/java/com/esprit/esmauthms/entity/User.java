// src/main/java/com/esprit/esmauthms/entity/User.java
package com.esprit.esmauthms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // Business identifier
    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(name = "cin", unique = true, length = 8)
    private String cin;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // simple string for role
    @Column(nullable = false)
    @Builder.Default
    private String role = "USER";

    private String firstName;
    private String lastName;

    private String avatarUrl;

    private String phoneNumber;

    private String address;

    // simple string for status (e.g. ACTIVE, INACTIVE, SUSPENDED)
    private String status;

    private boolean isEmailVerified;

    private boolean twoFactorEnabled;

    private String twoFactorSecret;

    private String twoFactorCode;

    private LocalDateTime twoFactorCodeExpiresAt;

    private String passwordResetToken;

    private LocalDateTime passwordResetExpiresAt;

    // NEW: email verification token + expiry
    private String emailVerificationToken;

    private LocalDateTime emailVerificationExpiresAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    private LocalDateTime lastLoginAt;

    private LocalDateTime deletedAt;

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }
}
