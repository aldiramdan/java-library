package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.request.ChangePasswordRequest;
import com.aldiramdan.library.model.dto.request.UserRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.entity.User;
import com.aldiramdan.library.repository.UserRepository;
import com.aldiramdan.library.service.UserService;
import com.aldiramdan.library.validator.UserValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private ResponseData responseData;

    @Override
    public ResponseData getAll() {
        List<User> listUser = userRepository.findAll();

        responseData = new ResponseData(200, "Success", listUser);
        return responseData;
    }

    @Override
    public ResponseData getById(Long id) throws Exception {
        Optional<User> findUser = userRepository.findById(id);
        userValidator.validateUserNotFound(findUser);

        responseData = new ResponseData(200, "Success", findUser);
        return responseData;
    }

    @Override
    public ResponseData getByUsername(String username) throws Exception {
        Optional<User> findUser = userRepository.findByUsername(username);
        userValidator.validateUserNotFound(findUser);

        responseData = new ResponseData(200, "Success", findUser);
        return responseData;
    }

    @Override
    public ResponseData update(String username, UserRequest request) throws Exception {
        Optional<User> findUser = userRepository.findByUsername(username);
        userValidator.validateUserNotFound(findUser);

        User user = findUser.get();

        if (user.getUsername() != request.getUsername() || user.getEmail() != request.getEmail()) {
            if (user.getUsername() != request.getUsername()) {
                Optional<User> findByUsername = userRepository.findByUsername(request.getUsername());
                userValidator.validateUsernameIsExists(findByUsername);

                user.setUsername(request.getUsername());
            }

            if (user.getEmail() != request.getEmail()) {
                Optional<User> findByEmail = userRepository.findByEmail(request.getEmail());
                userValidator.validateEmailIsExists(findByEmail);

                user.setEmail(request.getEmail());
            }
        }

        user.setName(request.getName());
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());

        userRepository.save(user);

        responseData = new ResponseData(200, "Success", user);
        return responseData;
    }

    @Override
    public ResponseData changePassword(String username, ChangePasswordRequest request) throws Exception {
        userValidator.validateInvalidNewPassword(request.getNewPassword(), request.getConfirmPassword());

        Optional<User> findUser = userRepository.findByUsername(username);
        userValidator.validateUserNotFound(findUser);

        User user = findUser.get();
        userValidator.validateInvalidOldPassword(request.getOldPassword(), user.getPassword());

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        responseData = new ResponseData(200, "Success", null);
        return responseData;
    }

    @Override
    public ResponseData delete(String username) throws Exception {
        Optional<User> findUser = userRepository.findByUsername(username);
        userValidator.validateUserNotFound(findUser);

        User user = findUser.get();
        userValidator.validateUserIsAlreadyDeleted(user);

        user.setIsDeleted(true);

        userRepository.save(user);

        responseData = new ResponseData(200, "Successfully recovery user", null);
        return responseData;
    }
}
