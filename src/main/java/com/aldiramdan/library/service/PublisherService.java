package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.PublisherRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface PublisherService {
    ResponseData getAll();
    ResponseData getById(Long id);
    ResponseData add(PublisherRequest request);
    ResponseData update(Long id, PublisherRequest request);
    ResponseData delete(Long id);
    ResponseData recovery(Long id);
}
