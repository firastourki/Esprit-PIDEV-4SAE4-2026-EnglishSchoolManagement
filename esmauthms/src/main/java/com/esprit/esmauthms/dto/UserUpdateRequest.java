package com.esprit.esmauthms.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdateRequest {

    @Pattern(regexp = "^\\d{8}$", message = "CIN must be exactly 8 digits")
    private String cin;

    private String email;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String status;
    private String role; // admin can change
}
