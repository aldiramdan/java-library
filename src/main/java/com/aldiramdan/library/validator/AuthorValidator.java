package com.aldiramdan.library.validator;

import com.aldiramdan.library.exception.custom.ConflictException;
import com.aldiramdan.library.exception.custom.NotFoundException;
import com.aldiramdan.library.exception.custom.NotProcessException;
import com.aldiramdan.library.model.entity.Author;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthorValidator {
    public void validateAuthorNotFound(Optional<Author> findAuthor) throws Exception {
        if (findAuthor.isEmpty()) {
            throw new NotFoundException("Author is not found!");
        }
    }

    public void validateAuthorIsExists(Optional<Author> findAuthor) throws Exception {
        if (findAuthor.isPresent()) {
            throw new ConflictException("Author has been exists!");
        }
    }

    public void validateAuthorIsAlreadyDeleted(Optional<Author> findAuthor) throws Exception {
        if (Objects.nonNull(findAuthor.get().getIsDeleted()) && findAuthor.get().getIsDeleted()) {
            throw new NotProcessException("Author is already deleted!");
        }
    }

    public void validateAuthorIsAlreadyRecovery(Optional<Author> findAuthor) throws Exception {
        if (!findAuthor.get().getIsDeleted()) {
            throw new NotProcessException("Author is already recovered!");
        }
    }
}
