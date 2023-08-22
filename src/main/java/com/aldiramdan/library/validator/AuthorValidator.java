package com.aldiramdan.library.validator;

import com.aldiramdan.library.exception.custom.FoundException;
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
            throw new FoundException("Author is found!");
        }
    }

    public void validateAuthorIsAlreadyDeleted(Author author) throws Exception {
        if (Objects.nonNull(author.getIsDeleted()) && author.getIsDeleted()) {
            throw new NotProcessException("Author is already deleted!");
        }
    }
}
