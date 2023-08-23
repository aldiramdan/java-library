package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.request.CategoryRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.entity.Category;
import com.aldiramdan.library.repository.CategoryRepository;
import com.aldiramdan.library.service.CategoryService;
import com.aldiramdan.library.validator.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryValidator categoryValidator;

    private ResponseData responseData;

    @Override
    public ResponseData getAll() {
        List<Category> listCategory = categoryRepository.findAll();

        return responseData = new ResponseData(200, "Success", listCategory);
    }

    @Override
    public ResponseData getById(Long id) throws Exception {
        Optional<Category> findCategory = categoryRepository.findById(id);
        categoryValidator.validateCategoryNotFound(findCategory);

        return responseData = new ResponseData(200, "Success", findCategory);
    }

    @Override
    public ResponseData add(CategoryRequest request) throws Exception {
        Optional<Category> findCategoryByName = categoryRepository.findByName(request.getName());
        categoryValidator.validateCategoryIsExists(findCategoryByName);

        Category category = new Category();
        category.setName(request.getName());

        categoryRepository.save(category);

        return responseData = new ResponseData(201, "Success", category);
    }

    @Override
    public ResponseData update(Long id, CategoryRequest request) throws Exception {
        Optional<Category> findCategory = categoryRepository.findById(id);
        categoryValidator.validateCategoryNotFound(findCategory);

        Category category = findCategory.get();
        if (category.getName() != request.getName()) {
            Optional<Category> findCategoryByName = categoryRepository.findByName(request.getName());
            categoryValidator.validateCategoryIsExists(findCategoryByName);

            category.setName(request.getName());
        }
        category.setName(category.getName());

        categoryRepository.save(category);

        return responseData = new ResponseData(200, "Success", category);
    }

    @Override
    public ResponseData delete(Long id) throws Exception {
        Optional<Category> findCategory = categoryRepository.findById(id);
        categoryValidator.validateCategoryNotFound(findCategory);

        Category category = findCategory.get();
        categoryValidator.validateCategoryIsAlreadyDeleted(category);

        category.setIsDeleted(true);

        categoryRepository.save(category);

        return responseData = new ResponseData(200, "Success", null);
    }

    @Override
    public ResponseData recovery(Long id) throws Exception {
        Optional<Category> findCategory = categoryRepository.findById(id);
        categoryValidator.validateCategoryNotFound(findCategory);

        Category category = findCategory.get();
        categoryValidator.validateCategoryIsAlreadyDeleted(category);

        category.setIsDeleted(false);

        categoryRepository.save(category);

        return responseData = new ResponseData(200, "Successfully recovery category", null);
    }
}
