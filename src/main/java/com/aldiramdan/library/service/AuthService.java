package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.*;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface AuthService {
    ResponseData login(LoginRequest request) throws Exception;
    ResponseData register(RegisterRequest request) throws Exception;
    ResponseData registerConfirm(String tokenCode) throws Exception;
    ResponseData recover(RecoveryRequest request) throws Exception;
    ResponseData recoveryConfirm(String tokenCode) throws Exception;
    ResponseData recoveryForgotPassword(RecoveryRequest request) throws Exception;
    ResponseData recoveryForgotPasswordConfirm(VerificationCodeRequest request) throws Exception;
    ResponseData recoveryResetPassword(ResetPasswordRequest request) throws Exception;
    ResponseData refreshToken(String authHeader) throws Exception;
}
