package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.*;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private ResponseData responseData;

    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(@Valid @RequestBody LoginRequest request) throws Exception {
        responseData = authService.login(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseData> register(@Valid @RequestBody RegisterRequest request) throws Exception {
        responseData = authService.register(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<ResponseData> registerConfirm(@RequestParam String token) throws Exception {
        responseData = authService.registerConfirm(token);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/recovery")
    public ResponseEntity<ResponseData> recovery(@Valid @RequestBody RecoveryRequest request) throws Exception {
        responseData = authService.recover(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @GetMapping("/recovery/confirm")
    public ResponseEntity<ResponseData> recoveryConfirm(@RequestParam String token) throws Exception {
        responseData = authService.recoveryConfirm(token);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/recovery/forgot-password")
    public ResponseEntity<ResponseData> recoveryForgotPassword(@Valid @RequestBody RecoveryRequest request) throws Exception {
        responseData = authService.recoveryForgotPassword(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/recovery/forgot-password/confirm")
    public ResponseEntity<ResponseData> recoveryForgotPasswordConfirm(@Valid @RequestBody VerificationCodeRequest request) throws Exception {
        responseData = authService.recoveryForgotPasswordConfirm(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PatchMapping("/recovery/reset-password")
    public ResponseEntity<ResponseData> recoveryForgotPassword(@Valid @RequestBody ResetPasswordRequest request) throws Exception {
        responseData = authService.recoveryResetPassword(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseData> refreshToken(@RequestHeader("X-AUTH-REFRESH") String refreshToken) throws IOException {
        responseData = authService.refreshToken(refreshToken);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }
}
