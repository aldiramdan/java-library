package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.config.jwt.JwtService;
import com.aldiramdan.library.model.dto.request.LoginRequest;
import com.aldiramdan.library.model.dto.request.RegisterRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.dto.response.ResponseToken;
import com.aldiramdan.library.model.entity.Role;
import com.aldiramdan.library.model.entity.Token;
import com.aldiramdan.library.model.entity.TokenType;
import com.aldiramdan.library.model.entity.User;
import com.aldiramdan.library.repository.TokenRepository;
import com.aldiramdan.library.repository.UserRepository;
import com.aldiramdan.library.service.AuthService;
import com.aldiramdan.library.utils.GenerateRandom;
import com.aldiramdan.library.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private TokenRepository tokenRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ResponseData responseData;

    @Override
    public ResponseData login(LoginRequest request) throws Exception {
        Optional<User> findByUsername = userRepository.findByUsername(request.getUsername());
        userValidator.validateUserNotFound(findByUsername);
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

        return responseData = new ResponseData(201, "Success", user);
    }

    public ResponseData refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        refreshToken = authHeader.substring(7);
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
