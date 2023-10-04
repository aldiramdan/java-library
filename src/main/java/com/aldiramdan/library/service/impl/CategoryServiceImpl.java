package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.request.CategoryRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.entity.Category;
import com.aldiramdan.library.repository.CategoryRepository;
import com.aldiramdan.library.service.CategoryService;
import com.aldiramdan.library.validator.CategoryValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryValidator categoryValidator;

    @Override
    public ResponseData getAll() {
        List<Category> listCategory = categoryRepository.findAll();

        return new ResponseData(200, "Success", listCategory);
    }

    @Override
    public ResponseData getById(Long id) {
        Optional<Category> findCategory = categoryRepository.findById(id);
        categoryValidator.validateCategoryNotFound(findCategory);

        return new ResponseData(200, "Success", findCategory);
    }

    @Override
    public ResponseData add(CategoryRequest request) {
        Optional<Category> findCategoryByName = categoryRepository.findByName(request.getName());
        categoryValidator.validateCategoryIsExists(findCategoryByName);

        Category category = new Category();
        category.setName(request.getName());
        categoryRepository.save(category);

        return new ResponseData(201, "Success", category);
    }

    @Override
    public ResponseData update(Long id, CategoryRequest request) {
        Optional<Category> findCategory = categoryRepository.findById(id);
        categoryValidator.validateCategoryNotFound(findCategory);

        if (!findCategory.get().getName().equals(request.getName())) {
            Optional<Category> findCategoryByName = categoryRepository.findByName(request.getName());
            categoryValidator.validateCategoryIsExists(findCategoryByName);
            findCategory.get().setName(request.getName());
        }
        categoryRepository.save(findCategory.get());

        return new ResponseData(200, "Success", findCategory.get());
    }

    @Override
    public ResponseData delete(Long id) {
        Optional<Category> findCategory = categoryRepository.findById(id);
        categoryValidator.validateCategoryNotFound(findCategory);
        categoryValidator.validateCategoryIsAlreadyDeleted(findCategory);

        findCategory.get().setIsDeleted(true);
        categoryRepository.save(findCategory.get());

        return new ResponseData(200, "Successfully deleted category", null);
    }

    @Override
    public ResponseData recovery(Long id) {
        Optional<Category> findCategory = categoryRepository.findById(id);
        categoryValidator.validateCategoryNotFound(findCategory);
        categoryValidator.validateCategoryIsAlreadyRecovery(findCategory);

        findCategory.get().setIsDeleted(false);
        categoryRepository.save(findCategory.get());

        return new ResponseData(200, "Successfully recovery category", null);
    }
}
