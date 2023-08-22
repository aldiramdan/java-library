package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.LoginRequest;
import com.aldiramdan.library.model.dto.request.RegisterRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {
    ResponseData login(LoginRequest request) throws Exception;

    ResponseData register(RegisterRequest request) throws Exception;

    ResponseData refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
