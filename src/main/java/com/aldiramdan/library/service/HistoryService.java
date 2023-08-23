package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.response.ResponseData;

public interface HistoryService {
    ResponseData getByUsername(String username);
}
