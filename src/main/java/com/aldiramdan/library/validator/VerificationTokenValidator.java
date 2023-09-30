package com.aldiramdan.library.validator;

import com.aldiramdan.library.exception.custom.NotFoundException;
import com.aldiramdan.library.exception.custom.NotProcessException;
import com.aldiramdan.library.model.entity.VerificationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class VerificationTokenValidator {
    public void validateVerificationTokenNotFound(Optional<VerificationToken> findToken) throws Exception {
        if (findToken.isEmpty()) {
            throw new NotFoundException("TokenCode is not found!");
        }
    }

    public void validateVerificationTokenAlreadyConfirm(Optional<VerificationToken> findToken) throws Exception {
        if (Objects.nonNull(findToken.get().getConfirmedAt())) {
            throw new NotProcessException("Account is already verification!");
        }
    }

    public void validateVerificationTokenAlreadyExpire(Optional<VerificationToken> findToken) throws Exception {
        if (findToken.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new NotProcessException("Token is already expired!");
        }
    }
}
