package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.AuthorRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface AuthorService {
    ResponseData getAll();
    ResponseData getById(Long id);
    ResponseData add(AuthorRequest request);
    ResponseData update(Long id, AuthorRequest request);
    ResponseData delete(Long id);
    ResponseData recovery(Long id);
}
