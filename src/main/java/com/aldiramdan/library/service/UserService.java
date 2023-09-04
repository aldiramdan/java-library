package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.ChangePasswordRequest;
import com.aldiramdan.library.model.dto.request.UserRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface UserService {
    ResponseData getAll();

    ResponseData getById(Long id) throws Exception;

    ResponseData getByUsername(Long id) throws Exception;

    ResponseData update(Long id, UserRequest request) throws Exception;

    ResponseData changePassword(Long id, ChangePasswordRequest request) throws Exception;

    ResponseData delete(Long id) throws Exception;


}
