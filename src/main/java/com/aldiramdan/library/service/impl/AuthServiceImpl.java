package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.request.*;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.dto.response.ResponseToken;
import com.aldiramdan.library.model.dto.response.ResponseUser;
import com.aldiramdan.library.model.entity.*;
import com.aldiramdan.library.repository.RecoveryTokenRepository;
import com.aldiramdan.library.repository.UserRepository;
import com.aldiramdan.library.repository.VerificationCodeRepository;
import com.aldiramdan.library.repository.VerificationTokenRepository;
import com.aldiramdan.library.security.jwt.JwtService;
import com.aldiramdan.library.service.AuthService;
import com.aldiramdan.library.service.SenderMailService;
import com.aldiramdan.library.utils.GenerateRandom;
import com.aldiramdan.library.validator.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthValidator authValidator;
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final RecoveryTokenRepository recoveryTokenRepository;
    private final RecoveryTokenValidator recoveryTokenValidator;
    private final VerificationCodeRepository verificationCodeRepository;
    private final VerificationTokenValidator verificationTokenValidator;
    private final VerificationTokenRepository verificationTokenRepository;
    private final VerificationCodeValidator verificationCodeValidator;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final SenderMailService senderMailService;

    @Override
    public ResponseData login(LoginRequest request) {
        Optional<User> findByUsername = userRepository.findByUsername(request.getUsername());
        userValidator.validateUserNotFound(findByUsername);
        userValidator.validateUserNotIsActives(findByUsername);
        userValidator.validateUserIsAlreadyDeleted(findByUsername);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String accessToken = jwtService.generateToken(findByUsername.get());
        String refreshToken = jwtService.generateRefreshToken(findByUsername.get());

        return new ResponseData(200, "Success", new ResponseToken(accessToken, refreshToken));
    }

    @Override
    public ResponseData register(RegisterRequest request) throws Exception {
        Optional<User> findByUsername = userRepository.findByUsername(request.getUsername());
        userValidator.validateUserUsernameIsExists(findByUsername);

        Optional<User> findByEmail = userRepository.findByEmail(request.getEmail());
        userValidator.validateUserEmailIsExists(findByEmail);
        userValidator.validateUserCheckPasswordStrength(request.getPassword());

        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        String tokenCode = GenerateRandom.token();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(tokenCode);
        verificationToken.setExpiresAt(expiresAt);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);

        senderMailService.confirmRegister(user, tokenCode);
        return new ResponseData(201, "Success", new ResponseUser(user));
    }

    @Override
    public ResponseData registerConfirm(String token) {
        Optional<VerificationToken> findToken = verificationTokenRepository.findByToken(token);
        verificationTokenValidator.validateVerificationTokenNotFound(findToken);
        verificationTokenValidator.validateVerificationTokenAlreadyConfirm(findToken);
        verificationTokenValidator.validateVerificationTokenAlreadyExpire(findToken);

        findToken.get().setConfirmedAt(LocalDateTime.now());
        verificationTokenRepository.save(findToken.get());

        findToken.get().getUser().setIsActives(true);
        userRepository.save(findToken.get().getUser());

        return new ResponseData(200, "Successfully verification account", null);
    }

    @Override
    public ResponseData recovery(RecoveryRequest request) throws Exception {
        Optional<User> findByEmail = userRepository.findByEmail(request.getEmail());
        userValidator.validateUserNotFound(findByEmail);
        userValidator.validateUserIsAlreadyRecovery(findByEmail);

        String tokenCode = GenerateRandom.token();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
        RecoveryToken recoveryToken = new RecoveryToken();
        recoveryToken.setToken(tokenCode);
        recoveryToken.setExpiresAt(expiresAt);
        recoveryToken.setUser(findByEmail.get());
        recoveryTokenRepository.save(recoveryToken);

        senderMailService.recoveryAccount(findByEmail.get(), tokenCode);
        return new ResponseData(200, "Successfully send mail recovery account", null);
    }

    @Override
    public ResponseData recoveryConfirm(String token) {
        Optional<RecoveryToken> findToken = recoveryTokenRepository.findByToken(token);
        recoveryTokenValidator.validateRecoveryTokenNotFound(findToken);
        recoveryTokenValidator.validateRecoveryTokenAlreadyConfirm(findToken);
        recoveryTokenValidator.validateRecoveryTokenAlreadyExpire(findToken);

        findToken.get().setConfirmedAt(LocalDateTime.now());
        recoveryTokenRepository.save(findToken.get());

        findToken.get().getUser().setIsDeleted(false);
        userRepository.save(findToken.get().getUser());

        return new ResponseData(200, "Successfully recovered account", null);
    }

    @Override
    public ResponseData recoveryForgotPassword(RecoveryRequest request) throws Exception {
        Optional<User> findByEmail = userRepository.findByEmail(request.getEmail());
        userValidator.validateUserNotFound(findByEmail);
        userValidator.validateUserNotIsActives(findByEmail);
        userValidator.validateUserIsAlreadyDeleted(findByEmail);

        String tokenCode = GenerateRandom.code();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(tokenCode);
        verificationCode.setExpiresAt(expiresAt);
        verificationCode.setUser(findByEmail.get());
        verificationCodeRepository.save(verificationCode);

        senderMailService.forgotPassword(findByEmail.get(), tokenCode);
        return new ResponseData(200, "Successfully send mail forgot password", null);
    }

    @Override
    public ResponseData recoveryForgotPasswordConfirm(VerificationCodeRequest request) {
        Optional<User> findByEmail = userRepository.findByEmail(request.getEmail());
        userValidator.validateUserNotFound(findByEmail);

        Optional<VerificationCode> findCode = verificationCodeRepository.findByCode(request.getCode());
        verificationCodeValidator.validateVerificationCodeNotFound(findCode);
        verificationCodeValidator.validateVerificationCodeAlreadyConfirm(findCode);
        verificationCodeValidator.validateVerificationCodeAlreadyExpire(findCode);

        findCode.get().setConfirmedAt(LocalDateTime.now());
        verificationCodeRepository.save(findCode.get());

        return new ResponseData(200, "Successfully verification account", null);
    }

    @Override
    public ResponseData recoveryResetPassword(ResetPasswordRequest request) {
        userValidator.validateUserPasswordNotMatch(request.getNewPassword(), request.getConfirmPassword());
        userValidator.validateUserCheckPasswordStrength(request.getConfirmPassword());

        Optional<VerificationCode> findCode = verificationCodeRepository.findByCode(request.getCode());
        verificationCodeValidator.validateVerificationCodeNotFound(findCode);
        verificationCodeValidator.validateVerificationCodeNotAlreadyConfirm(findCode);
        verificationCodeValidator.validateVerificationCodeAlreadyExpire(findCode);

        findCode.get().getUser().setPassword(passwordEncoder.encode(request.getConfirmPassword()));
        userRepository.save(findCode.get().getUser());

        return new ResponseData(200, "Successfully reset password", null);
    }

    public ResponseData refreshToken(String authHeader) {
        authValidator.validateAuthHeaderNotFound(authHeader);
        String refreshToken = authHeader.substring(7);
        String username = jwtService.extractUsername(refreshToken);
        Optional<User> findUser = userRepository.findByUsername(username);
        userValidator.validateUserNotFound(findUser);

        authValidator.validateAuthTokenInvalid(refreshToken, findUser.get());
        String accessToken = jwtService.generateToken(findUser.get());

        return new ResponseData(200, "Success", new ResponseToken(accessToken, refreshToken));
    }
}
