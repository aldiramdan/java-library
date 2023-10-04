package com.aldiramdan.library.service;

import com.aldiramdan.library.model.dto.request.ChangePasswordRequest;
import com.aldiramdan.library.model.dto.request.UserRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;

public interface UserService {
    ResponseData getAll();
    ResponseData getById(Long id);
    ResponseData getByUsername(Long id);
    ResponseData update(Long id, UserRequest request);
    ResponseData changePassword(Long id, ChangePasswordRequest request);
    ResponseData delete(Long id);
}
