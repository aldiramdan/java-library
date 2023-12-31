package com.aldiramdan.library.validator;

import com.aldiramdan.library.exception.custom.BadRequestException;
import com.aldiramdan.library.exception.custom.NotFoundException;
import com.aldiramdan.library.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthValidator {
    private final JwtService jwtService;

    public void validateAuthHeaderNotFound(String authHeader) {
        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new NotFoundException("AuthHeader Token not found!");
        }
    }

    public void validateAuthTokenInvalid(String refreshToken, UserDetails user) {
        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new BadRequestException("JWT Token invalid!");
        }
    }
}
