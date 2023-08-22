package com.aldiramdan.library.validator;

import com.aldiramdan.library.exception.custom.FoundException;
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
            throw new FoundException("Category is found!");
        }
    }

    public void validateCategoryIsAlreadyDeleted(Category category) throws Exception {
        if (Objects.nonNull(category.getIsDeleted()) && category.getIsDeleted()) {
            throw new NotProcessException("Category is already deleted!");
        }
    }
}
