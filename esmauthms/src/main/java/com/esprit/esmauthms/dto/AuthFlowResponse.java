// src/main/java/com/esprit/esmauthms/dto/AuthFlowResponse.java
package com.esprit.esmauthms.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Response used for multi-step login flow.
 */
@Data
@Builder
public class AuthFlowResponse {

    private String accessToken;          // only set when login is fully completed

    private boolean emailVerified;       // current email status
    private String accountStatus;        // e.g. ACTIVE, SUSPENDED, null

    private boolean twoFactorRequired;   // true when user must submit 2FA code
    private String maskedEmail;          // e.g. j***@example.com for UI display
}
