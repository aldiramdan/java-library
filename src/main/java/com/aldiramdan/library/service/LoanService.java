package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.LoanRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface LoanService {
    ResponseData getAll();
    ResponseData getById(Long id);
    ResponseData add(LoanRequest request);
    ResponseData update(Long id, LoanRequest request);
    ResponseData updateStatus(Long id, LoanRequest request);
    ResponseData delete(Long id);
    ResponseData recovery(Long id);
}
