package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.*;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(@Valid @RequestBody LoginRequest request) {
        ResponseData responseData = authService.login(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseData> register(@Valid @RequestBody RegisterRequest request) throws Exception {
        ResponseData responseData = authService.register(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<ResponseData> registerConfirm(@RequestParam String token) {
        ResponseData responseData = authService.registerConfirm(token);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/recovery")
    public ResponseEntity<ResponseData> recovery(@Valid @RequestBody RecoveryRequest request) throws Exception {
        ResponseData responseData = authService.recovery(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @GetMapping("/recovery/confirm")
    public ResponseEntity<ResponseData> recoveryConfirm(@RequestParam String token) {
        ResponseData responseData = authService.recoveryConfirm(token);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/recovery/forgot-password")
    public ResponseEntity<ResponseData> recoveryForgotPassword(@Valid @RequestBody RecoveryRequest request) throws Exception {
        ResponseData responseData = authService.recoveryForgotPassword(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/recovery/forgot-password/confirm")
    public ResponseEntity<ResponseData> recoveryForgotPasswordConfirm(@Valid @RequestBody VerificationCodeRequest request) {
        ResponseData responseData = authService.recoveryForgotPasswordConfirm(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PatchMapping("/recovery/reset-password")
    public ResponseEntity<ResponseData> recoveryForgotPassword(@Valid @RequestBody ResetPasswordRequest request) {
        ResponseData responseData = authService.recoveryResetPassword(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseData> refreshToken(@RequestHeader("X-AUTH-REFRESH") String refreshToken) {
        ResponseData responseData = authService.refreshToken(refreshToken);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }
}
