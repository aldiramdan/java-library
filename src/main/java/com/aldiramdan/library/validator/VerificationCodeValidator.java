package com.aldiramdan.library.validator;

import com.aldiramdan.library.exception.custom.NotFoundException;
import com.aldiramdan.library.model.entity.VerificationCode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class VerificationCodeValidator {
    public void validateVerificationCodeNotFound(Optional<VerificationCode> findToken) throws Exception {
        if (findToken.isEmpty()) {
            throw new NotFoundException("Code is not found!");
        }
    }

    public void validateVerificationCodeAlreadyConfirm(Optional<VerificationCode> findToken) throws Exception {
        if (Objects.nonNull(findToken.get().getConfirmedAt())) {
            throw new IllegalStateException("Account is already verification!");
        }
    }

    public void validateVerificationCodeNotAlreadyConfirm(Optional<VerificationCode> findToken) throws Exception {
        if (Objects.isNull(findToken.get().getConfirmedAt())) {
            throw new IllegalStateException("Account is not already verification!, please verification");
        }
    }

    public void validateVerificationCodeAlreadyExpire(Optional<VerificationCode> findToken) throws Exception {
        if (findToken.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Code is already expired!");
        }
    }
}
