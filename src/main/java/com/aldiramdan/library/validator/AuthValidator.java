package com.aldiramdan.library.validator;

import com.aldiramdan.library.exception.custom.NotFoundException;
import com.aldiramdan.library.model.entity.RecoveryToken;
import com.aldiramdan.library.model.entity.VerificationCode;
import com.aldiramdan.library.model.entity.VerificationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthValidator {
    public void validateVerificationTokenNotFound(Optional<VerificationToken> findToken) throws Exception {
        if (findToken.isEmpty()) {
            throw new NotFoundException("TokenCode is not found!");
        }
    }

    public void validateAlreadyVerificationToken(Optional<VerificationToken> findToken) throws Exception {
        if (Objects.nonNull(findToken.get().getConfirmedAt())) {
            throw new IllegalStateException("Account is already verification!");
        }
    }

    public void validateExpireVerificationToken(Optional<VerificationToken> findToken) throws Exception {
        if (findToken.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token is already expired!");
        }
    }

    public void validateRecoveryTokenNotFound(Optional<RecoveryToken> findToken) throws Exception {
        if (findToken.isEmpty()) {
            throw new NotFoundException("TokenCode is not found!");
        }
    }

    public void validateAlreadyRecovery(Optional<RecoveryToken> findToken) throws Exception {
        if (Objects.nonNull(findToken.get().getConfirmedAt())) {
            throw new IllegalStateException("Account is already recovered!");
        }
    }

    public void validateExpireRecovery(Optional<RecoveryToken> findToken) throws Exception {
        if (findToken.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token is already expired!");
        }
    }

    public void validateVerificationCodeNotFound(Optional<VerificationCode> findToken) throws Exception {
        if (findToken.isEmpty()) {
            throw new NotFoundException("Code is not found!");
        }
    }

    public void validateAlreadyVerificationCode(Optional<VerificationCode> findToken) throws Exception {
        if (Objects.nonNull(findToken.get().getConfirmedAt())) {
            throw new IllegalStateException("Account is already verification!");
        }
    }

    public void validateNotAlreadyVerificationCode(Optional<VerificationCode> findToken) throws Exception {
        if (Objects.isNull(findToken.get().getConfirmedAt())) {
            throw new IllegalStateException("Account is not already verification!, please verification");
        }
    }

    public void validateExpireVerificationCode(Optional<VerificationCode> findToken) throws Exception {
        if (findToken.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Code is already expired!");
        }
    }
}
