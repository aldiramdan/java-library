package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.CategoryRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface CategoryService {
    ResponseData getAll();

    ResponseData getById(Long id) throws Exception;

    ResponseData add(CategoryRequest request) throws Exception;

    ResponseData update(Long id, CategoryRequest request) throws Exception;

    ResponseData delete(Long id) throws Exception;

    ResponseData recovery(Long id) throws Exception;
}
