package com.aldiramdan.library.validator;

import com.aldiramdan.library.exception.custom.NotFoundException;
import com.aldiramdan.library.exception.custom.NotProcessException;
import com.aldiramdan.library.model.entity.RecoveryToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class RecoveryTokenValidator {
    public void validateRecoveryTokenNotFound(Optional<RecoveryToken> findToken) {
        if (findToken.isEmpty()) {
            throw new NotFoundException("TokenCode is not found!");
        }
    }

    public void validateRecoveryTokenAlreadyConfirm(Optional<RecoveryToken> findToken) {
        if (Objects.nonNull(findToken.get().getConfirmedAt())) {
            throw new NotProcessException("Account is already recovered!");
        }
    }

    public void validateRecoveryTokenAlreadyExpire(Optional<RecoveryToken> findToken) {
        if (findToken.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new NotProcessException("Token is already expired!");
        }
    }
}
