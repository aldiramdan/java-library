package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.LoanRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface LoanService {
    ResponseData getAll();
    ResponseData getById(Long id) throws Exception;
    ResponseData add(LoanRequest request) throws Exception;
    ResponseData update(Long id, LoanRequest request) throws Exception;
    ResponseData updateStatus(Long id, LoanRequest request) throws Exception;
    ResponseData delete(Long id) throws Exception;
    ResponseData recovery(Long id) throws Exception;
}
