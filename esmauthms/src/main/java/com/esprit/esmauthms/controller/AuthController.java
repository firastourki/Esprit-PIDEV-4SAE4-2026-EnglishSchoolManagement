// src/main/java/com/esprit/esmauthms/controller/AuthController.java
package com.esprit.esmauthms.controller;

import com.esprit.esmauthms.dto.*;
import com.esprit.esmauthms.service.AuthService;
import com.esprit.esmauthms.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthFlowResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/2fa/verify")
    public AuthFlowResponse verifyTwoFactor(@RequestBody TwoFactorVerifyRequest request) {
        return authService.verifyTwoFactor(request);
    }

    @PostMapping("/email/send-verification")
    public void sendEmailVerification(@RequestBody EmailVerificationRequest request) {
        authService.sendEmailVerificationLink(request);
    }

    @PostMapping("/email/verify")
    public void verifyEmail(@RequestBody EmailVerifyRequest request) {
        authService.verifyEmail(request);
    }

    @PostMapping("/password/reset-request")
    public void requestPasswordReset(@RequestBody PasswordResetRequest request) {
        authService.requestPasswordReset(request);
    }

    @PostMapping("/password/reset-confirm")
    public void confirmPasswordReset(@RequestBody PasswordResetConfirmRequest request) {
        authService.confirmPasswordReset(request);
    }

    // 2FA enable/disable for current user (requires Authorization)
    @PostMapping("/2fa/enable")
    public void enableTwoFactor(HttpServletRequest request) {
        UUID userId = extractUserId(request);
        authService.enableTwoFactor(userId);
    }

    @PostMapping("/2fa/disable")
    public void disableTwoFactor(HttpServletRequest request) {
        UUID userId = extractUserId(request);
        authService.disableTwoFactor(userId);
    }

    private UUID extractUserId(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = header.substring(7);
        return jwtService.extractUserId(token);
    }
}
