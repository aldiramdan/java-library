package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.BookRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface BookService {
    ResponseData getAll(Boolean isBorrowed);
    ResponseData getById(Long id);
    ResponseData searchByName(String column, String name);
    ResponseData add(BookRequest request);
    ResponseData update(Long id, BookRequest request);
    ResponseData delete(Long id);
    ResponseData recovery(Long id);
}
