package com.aldiramdan.library.validator;

import com.aldiramdan.library.exception.custom.ConflictException;
import com.aldiramdan.library.exception.custom.NotFoundException;
import com.aldiramdan.library.exception.custom.NotProcessException;
import com.aldiramdan.library.model.entity.Category;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryValidator {
    public void validateCategoryNotFound(Optional<Category> findCategory) throws Exception {
        if (findCategory.isEmpty()) {
            throw new NotFoundException("Category is not found!");
        }
    }

    public void validateCategoryIsExists(Optional<Category> findCategory) throws Exception {
        if (findCategory.isPresent()) {
            throw new ConflictException("Category has been exists!");
        }
    }

    public void validateCategoryIsAlreadyDeleted(Optional<Category> findCategory) throws Exception {
        if (Objects.nonNull(findCategory.get().getIsDeleted()) && findCategory.get().getIsDeleted()) {
            throw new NotProcessException("Category is already deleted!");
        }
    }

    public void validateCategoryIsAlreadyRecovery(Optional<Category> findCategory) throws Exception {
        if (!findCategory.get().getIsDeleted()) {
            throw new NotProcessException("Category is already recovered!");
        }
    }
}
