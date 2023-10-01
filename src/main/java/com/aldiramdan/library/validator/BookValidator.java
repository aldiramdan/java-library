package com.aldiramdan.library.validator;

import com.aldiramdan.library.exception.custom.NotFoundException;
import com.aldiramdan.library.exception.custom.NotProcessException;
import com.aldiramdan.library.model.entity.Book;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class BookValidator {
    public void validateBookNotFound(Optional<Book> findBook) throws Exception {
        if (findBook.isEmpty()) {
            throw new NotFoundException("Book is not found!");
        }
    }

    public void validateBookIsBorrowed(Optional<Book> findBook) throws Exception {
        if (findBook.get().getIsBorrowed()) {
            throw new NotProcessException("Book is already borrowed!");
        }
    }

    public void validateBookIsAlreadyDeleted(Optional<Book> findBook) throws Exception {
        if (Objects.nonNull(findBook.get().getIsDeleted()) && findBook.get().getIsDeleted()) {
            throw new NotProcessException("Book is already deleted!");
        }
    }

    public void validateBookIsAlreadyRecovery(Optional<Book> findBook) throws Exception {
        if (!findBook.get().getIsDeleted()) {
            throw new NotProcessException("Book is already recovery!");
        }
    }
}
