package com.esprit.esmauthms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserCreateRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    // 8 digits exactly
    @Pattern(regexp = "^\\d{8}$", message = "CIN must be exactly 8 digits")
    private String cin;

    private String role; // for admin-created users

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String status;
}
