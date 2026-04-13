// src/main/java/com/esprit/esmauthms/dto/PasswordResetConfirmRequest.java
package com.esprit.esmauthms.dto;

import lombok.Data;

@Data
public class PasswordResetConfirmRequest {

    private String token;
    private String newPassword;
}
