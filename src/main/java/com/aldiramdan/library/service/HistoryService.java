package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.response.ResponseData;

public interface HistoryService {
    ResponseData getById(Long id);
}
