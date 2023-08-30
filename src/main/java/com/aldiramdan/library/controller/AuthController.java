package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.*;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.AuthService;
import com.aldiramdan.library.utils.Cookies;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    private ResponseData responseData;

    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(@Valid @RequestBody LoginRequest request) throws Exception {
        responseData = authService.login(request);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseData> register(@Valid @RequestBody RegisterRequest request) throws Exception {
        responseData = authService.register(request);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<ResponseData> registerConfirm(@RequestParam String token) throws Exception {
        responseData = authService.registerConfirm(token);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PostMapping("/recovery")
    public ResponseEntity<ResponseData> recovery(@Valid @RequestBody RecoveryRequest request) throws Exception {
        responseData = authService.recover(request);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @GetMapping("/recovery/confirm")
    public ResponseEntity<ResponseData> recoveryConfirm(@RequestParam String token) throws Exception {
        responseData = authService.recoveryConfirm(token);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PostMapping("/recovery/forgot-password")
    public ResponseEntity<ResponseData> recoveryForgotPassword(HttpServletResponse res, @Valid @RequestBody RecoveryRequest request) throws Exception {
        responseData = authService.recoveryForgotPassword(request);
        Cookies.setEmailCookie(res, request.getEmail());
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PostMapping("/recovery/forgot-password/confirm")
    public ResponseEntity<ResponseData> recoveryForgotPasswordConfirm(HttpServletRequest req, HttpServletResponse res, @Valid @RequestBody VerificationCodeRequest request) throws Exception {
        Cookie[] cookies = req.getCookies();

        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();

                if (name.equals("resetPasswordEmail")) {
                    responseData = authService.recoveryForgotPasswordConfirm(request, value);
                    break;
                }
            }
        }

        Cookies.setCodeCookie(res, request.getCode());
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PatchMapping("/recovery/reset-password")
    public ResponseEntity<ResponseData> recoveryForgotPassword(HttpServletRequest req, @Valid @RequestBody ResetPasswordRequest request) throws Exception {
        Cookie[] cookies = req.getCookies();

        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();

                if (name.equals("resetPasswordCode")) {
                    responseData = authService.recoveryResetPassword(request, value);
                    break;
                }
            }
        }
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseData> refreshToken(HttpServletRequest req, HttpServletResponse res) throws IOException {
        final String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        refreshToken = authHeader.substring(7);

        responseData = authService.refreshToken(refreshToken);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }
}
