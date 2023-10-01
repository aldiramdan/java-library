package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.PublisherRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface PublisherService {
    ResponseData getAll();
    ResponseData getById(Long id) throws Exception;
    ResponseData add(PublisherRequest request) throws Exception;
    ResponseData update(Long id, PublisherRequest request) throws Exception;
    ResponseData delete(Long id) throws Exception;
    ResponseData recovery(Long id) throws Exception;
}
