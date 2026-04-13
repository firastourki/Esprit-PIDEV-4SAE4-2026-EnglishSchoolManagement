// src/main/java/com/esprit/esmauthms/dto/EmailVerificationRequest.java
package com.esprit.esmauthms.dto;

import lombok.Data;

/**
 * Generic request to trigger email verification link.
 * For now we only need email.
 */
@Data
public class EmailVerificationRequest {

    private String email;
}
