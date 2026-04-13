package com.esprit.esmauthms.repository;

import com.esprit.esmauthms.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Page<User> findByEmailContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndCinContainingAndPhoneNumberContaining(
            String email,
            String firstName,
            String lastName,
            String cin,
            String phoneNumber,
            Pageable pageable
    );
}
