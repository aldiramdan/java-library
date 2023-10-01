package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.GenreRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface GenreService {
    ResponseData getAll();
    ResponseData getById(Long id) throws Exception;
    ResponseData add(GenreRequest request) throws Exception;
    ResponseData update(Long id, GenreRequest request) throws Exception;
    ResponseData delete(Long id) throws Exception;
    ResponseData recovery(Long id) throws Exception;
}
