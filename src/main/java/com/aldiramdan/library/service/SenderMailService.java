package com.aldiramdan.library.service;

import com.aldiramdan.library.model.entity.User;

public interface SenderMailService {
    void confirmRegister(User user, String token) throws Exception;
    void forgotPassword(User user, String code) throws Exception;
    void recoveryAccount(User user, String token) throws Exception;
}
