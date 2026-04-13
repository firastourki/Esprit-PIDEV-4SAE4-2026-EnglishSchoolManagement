// src/main/java/com/esprit/esmauthms/dto/TwoFactorVerifyRequest.java
package com.esprit.esmauthms.dto;

import lombok.Data;

@Data
public class TwoFactorVerifyRequest {

    private String email;
    private String code; // 6-digit code
}
