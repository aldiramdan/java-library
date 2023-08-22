package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.ChangePasswordRequest;
import com.aldiramdan.library.model.dto.request.UserRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface UserService {
    ResponseData getAll();

    ResponseData getById(Long id) throws Exception;

    ResponseData getByUsername(String username) throws Exception;

    ResponseData update(String username, UserRequest request) throws Exception;

    ResponseData changePassword(String username, ChangePasswordRequest request) throws Exception;

    ResponseData delete(String username) throws Exception;


}
