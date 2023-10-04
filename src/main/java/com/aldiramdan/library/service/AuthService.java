package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.*;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface AuthService {
    ResponseData login(LoginRequest request);
    ResponseData register(RegisterRequest request) throws Exception;
    ResponseData registerConfirm(String tokenCode);
    ResponseData recovery(RecoveryRequest request) throws Exception;
    ResponseData recoveryConfirm(String tokenCode);
    ResponseData recoveryForgotPassword(RecoveryRequest request) throws Exception;
    ResponseData recoveryForgotPasswordConfirm(VerificationCodeRequest request);
    ResponseData recoveryResetPassword(ResetPasswordRequest request);
    ResponseData refreshToken(String authHeader);
}
