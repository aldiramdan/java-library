package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.CategoryRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface CategoryService {
    ResponseData getAll();
    ResponseData getById(Long id);
    ResponseData add(CategoryRequest request);
    ResponseData update(Long id, CategoryRequest request);
    ResponseData delete(Long id);
    ResponseData recovery(Long id);
}
