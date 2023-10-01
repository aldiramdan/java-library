package com.aldiramdan.library.validator;

import com.aldiramdan.library.exception.custom.ConflictException;
import com.aldiramdan.library.exception.custom.NotFoundException;
import com.aldiramdan.library.exception.custom.NotProcessException;
import com.aldiramdan.library.model.entity.Genre;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class GenreValidator {
    public void validateGenreNotFound(Optional<Genre> findGenre) throws Exception {
        if (findGenre.isEmpty()) {
            throw new NotFoundException("Genre is not found!");
        }
    }

    public void validateGenreIsExists(Optional<Genre> findGenre) throws Exception {
        if (findGenre.isPresent()) {
            throw new ConflictException("Genre is found!");
        }
    }

    public void validateCategoryIsAlreadyDeleted(Optional<Genre> findGenre) throws Exception {
        if (Objects.nonNull(findGenre.get().getIsDeleted()) && findGenre.get().getIsDeleted()) {
            throw new NotProcessException("Genre is already deleted!");
        }
    }

    public void validateCategoryIsAlreadyRecovery(Optional<Genre> findGenre) throws Exception {
        if (!findGenre.get().getIsDeleted()) {
            throw new NotProcessException("Genre is already recovery!");
        }
    }
}
