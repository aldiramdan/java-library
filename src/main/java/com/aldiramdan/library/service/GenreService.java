package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.GenreRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface GenreService {
    ResponseData getAll();
    ResponseData getById(Long id);
    ResponseData add(GenreRequest request);
    ResponseData update(Long id, GenreRequest request);
    ResponseData delete(Long id);
    ResponseData recovery(Long id);
}
