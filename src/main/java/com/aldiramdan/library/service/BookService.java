package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.BookRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface BookService {
    ResponseData getAll(Boolean isBorrowed);
    ResponseData getById(Long id) throws Exception;
    ResponseData searchByName(String column, String name);
    ResponseData add(BookRequest request) throws Exception;
    ResponseData update(Long id, BookRequest request) throws Exception;
    ResponseData delete(Long id) throws Exception;
    ResponseData recovery(Long id) throws Exception;
}
