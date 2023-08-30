package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.config.jwt.JwtService;
import com.aldiramdan.library.config.mail.EmailSender;
import com.aldiramdan.library.model.dto.request.*;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.dto.response.ResponseToken;
import com.aldiramdan.library.model.dto.response.ResponseUser;
import com.aldiramdan.library.model.entity.*;
import com.aldiramdan.library.repository.*;
import com.aldiramdan.library.service.AuthService;
import com.aldiramdan.library.utils.GenerateRandom;
import com.aldiramdan.library.validator.AuthValidator;
import com.aldiramdan.library.validator.UserValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private AuthValidator authValidator;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private RecoveryTokenRepository recoveryTokenRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSender emailSender;

    private ResponseData responseData;

    @Override
    public ResponseData login(LoginRequest request) throws Exception {
        Optional<User> findByUsername = userRepository.findByUsername(request.getUsername());
        userValidator.validateUserNotFound(findByUsername);
        userValidator.validateUserNotIsActives(findByUsername);
        userValidator.validateUserIsAlreadyDeleted(findByUsername.get());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = findByUsername.get();

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        ResponseToken responseToken = new ResponseToken(
                accessToken,
                refreshToken
        );

        return responseData = new ResponseData(200, "Success", responseToken);
    }

    @Override
    public ResponseData register(RegisterRequest request) throws Exception {
        Optional<User> findByUsername = userRepository.findByUsername(request.getUsername());
        userValidator.validateUsernameIsExists(findByUsername);

        Optional<User> findByEmail = userRepository.findByEmail(request.getEmail());
        userValidator.validateEmailIsExists(findByEmail);

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

        emailSender.sendMail(user, tokenCode, "/register/confirm", "Confirm your account");

        ResponseUser result = new ResponseUser(user);
        return responseData = new ResponseData(201, "Success", result);
    }

    @Override
    public ResponseData registerConfirm(String token) throws Exception {
        Optional<VerificationToken> findToken = verificationTokenRepository.findByToken(token);
        authValidator.validateVerificationTokenNotFound(findToken);
        authValidator.validateAlreadyVerificationToken(findToken);
        authValidator.validateExpireVerificationToken(findToken);

        VerificationToken verificationToken = findToken.get();
        verificationToken.setConfirmedAt(LocalDateTime.now());
        verificationTokenRepository.save(verificationToken);

        User user = findToken.get().getUser();
        user.setIsActives(true);
        userRepository.save(user);

        return responseData = new ResponseData(200, "Successfully verification account", null);
    }

    @Override
    public ResponseData recover(RecoveryRequest request) throws Exception {
        Optional<User> findByEmail = userRepository.findByEmail(request.getEmail());
        userValidator.validateUserNotFound(findByEmail);

        User user = findByEmail.get();

        String tokenCode = GenerateRandom.token();

        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
        RecoveryToken recoveryToken = new RecoveryToken();
        recoveryToken.setToken(tokenCode);
        recoveryToken.setExpiresAt(expiresAt);
        recoveryToken.setUser(user);

        recoveryTokenRepository.save(recoveryToken);

        emailSender.sendMail(user, tokenCode, "/recovery/confirm", "Recovery your account");
        return responseData = new ResponseData(200, "Successfully send mail recovery account", null);
    }

    @Override
    public ResponseData recoveryConfirm(String token) throws Exception {
        Optional<RecoveryToken> findToken = recoveryTokenRepository.findByToken(token);
        authValidator.validateRecoveryTokenNotFound(findToken);
        authValidator.validateAlreadyRecovery(findToken);
        authValidator.validateExpireRecovery(findToken);

        RecoveryToken recoveryToken = findToken.get();
        recoveryToken.setConfirmedAt(LocalDateTime.now());
        recoveryTokenRepository.save(recoveryToken);

        User user = findToken.get().getUser();
        user.setIsDeleted(false);
        userRepository.save(user);

        return responseData = new ResponseData(200, "Successfully recovered account", null);
    }

    @Override
    public ResponseData recoveryForgotPassword(RecoveryRequest request) throws Exception {
        Optional<User> findByEmail = userRepository.findByEmail(request.getEmail());
        userValidator.validateUserNotFound(findByEmail);
        userValidator.validateUserNotIsActives(findByEmail);
        userValidator.validateUserIsAlreadyDeleted(findByEmail.get());

        User user = findByEmail.get();

        String tokenCode = GenerateRandom.code();

        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(tokenCode);
        verificationCode.setExpiresAt(expiresAt);
        verificationCode.setUser(user);

        verificationCodeRepository.save(verificationCode);

        emailSender.sendMail(user, tokenCode, "/recovery/reset-password/confirm", "Forgot Password");
        return responseData = new ResponseData(200, "Successfully send mail forgot password", null);
    }

    @Override
    public ResponseData recoveryForgotPasswordConfirm(VerificationCodeRequest request, String email) throws Exception {
        Optional<User> findByEmail = userRepository.findByEmail(email);
        userValidator.validateUserNotFound(findByEmail);


        Optional<VerificationCode> findCode = verificationCodeRepository.findByCode(request.getCode());
        authValidator.validateVerificationCodeNotFound(findCode);
        authValidator.validateAlreadyVerificationCode(findCode);
        authValidator.validateExpireVerificationCode(findCode);
        userValidator.validateInvalidCookiesEmail(email, findCode.get().getUser().getEmail());

        VerificationCode verificationCode = findCode.get();
        verificationCode.setConfirmedAt(LocalDateTime.now());
        verificationCodeRepository.save(verificationCode);

        return responseData = new ResponseData(200, "Successfully verification account", null);
    }

    @Override
    public ResponseData recoveryResetPassword(ResetPasswordRequest request, String code) throws Exception {
        userValidator.validateInvalidNewPassword(request.getNewPassword(), request.getConfirmPassword());

        Optional<VerificationCode> findCode = verificationCodeRepository.findByCode(code);
        authValidator.validateVerificationCodeNotFound(findCode);
        authValidator.validateNotAlreadyVerificationCode(findCode);
        authValidator.validateExpireVerificationCode(findCode);
        authValidator.validateInvalidCookiesCode(code, findCode.get().getCode());

        User user = findCode.get().getUser();
        user.setPassword(request.getConfirmPassword());
        userRepository.save(user);

        return responseData = new ResponseData(200, "Successfully reset password", null);
    }

    public ResponseData refreshToken(String refreshToken) throws IOException {
        final String username;

        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            User user = this.userRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                ResponseToken responseToken = new ResponseToken(
                        accessToken,
                        refreshToken
                );

                responseData = new ResponseData(200, "Success", responseToken);
            }
        }
        return responseData;
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .tokenCode(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenCodeByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
