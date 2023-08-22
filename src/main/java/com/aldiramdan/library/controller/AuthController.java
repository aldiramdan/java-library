package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.LoginRequest;
import com.aldiramdan.library.model.dto.request.RegisterRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseData> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        responseData = authService.refreshToken(request, response);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }
}
