package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.AuthorRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface AuthorService {
    ResponseData getAll();
    ResponseData getById(Long id) throws Exception;
    ResponseData add(AuthorRequest request) throws Exception;
    ResponseData update(Long id, AuthorRequest request) throws Exception;
    ResponseData delete(Long id) throws Exception;
    ResponseData recovery(Long id) throws Exception;
}
