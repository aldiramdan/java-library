package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.request.ChangePasswordRequest;
import com.aldiramdan.library.model.dto.request.UserRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.dto.response.ResponseUser;
import com.aldiramdan.library.model.entity.User;
import com.aldiramdan.library.repository.UserRepository;
import com.aldiramdan.library.service.UserService;
import com.aldiramdan.library.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseData getAll() {
        List<User> listUser = userRepository.findAll();

        List<ResponseUser> listResult = new ArrayList<>();
        for (User u : listUser) {
            ResponseUser temp = new ResponseUser(u);
            listResult.add(temp);
        }

        return new ResponseData(200, "Success", listResult);
    }

    @Override
    public ResponseData getById(Long id) throws Exception {
        Optional<User> findUser = userRepository.findById(id);
        userValidator.validateUserNotFound(findUser);

        return new ResponseData(200, "Success", new ResponseUser(findUser.get()));
    }

    @Override
    public ResponseData getByUsername(Long id) throws Exception {
        Optional<User> findUser = userRepository.findById(id);
        userValidator.validateUserNotFound(findUser);

        return new ResponseData(200, "Success", new ResponseUser(findUser.get()));
    }

    @Override
    public ResponseData update(Long id, UserRequest request) throws Exception {
        Optional<User> findUser = userRepository.findById(id);
        userValidator.validateUserNotFound(findUser);

        Optional<User> findByUsername = userRepository.findByUsername(request.getUsername());
        if (!findUser.get().getUsername().equals(findByUsername.get().getUsername())) {
            userValidator.validateUserUsernameIsExists(findByUsername);
            findUser.get().setUsername(request.getUsername());
        }

        Optional<User> findByEmail = userRepository.findByEmail(request.getEmail());
        if (!findUser.get().getEmail().equals(findByEmail.get().getEmail())) {
            userValidator.validateUserEmailIsExists(findByEmail);

            findUser.get().setEmail(request.getEmail());
            findUser.get().setIsActives(false);
        }

        findUser.get().setName(request.getName());
        userRepository.save(findUser.get());

        return new ResponseData(200, "Success", new ResponseUser(findUser.get()));
    }

    @Override
    public ResponseData changePassword(Long id, ChangePasswordRequest request) throws Exception {
        userValidator.validateUserPasswordNotMatch(request.getNewPassword(), request.getConfirmPassword());
        userValidator.validateUserCheckPasswordStrength(request.getConfirmPassword());

        Optional<User> findUser = userRepository.findById(id);
        userValidator.validateUserNotFound(findUser);
        userValidator.validateUserInvalidOldPassword(request.getOldPassword(), findUser.get().getPassword());

        findUser.get().setPassword(passwordEncoder.encode(request.getConfirmPassword()));
        userRepository.save(findUser.get());

        return new ResponseData(200, "Success updated password", null);
    }

    @Override
    public ResponseData delete(Long id) throws Exception {
        Optional<User> findUser = userRepository.findById(id);
        userValidator.validateUserNotFound(findUser);
        userValidator.validateUserIsAlreadyDeleted(findUser);

        findUser.get().setIsDeleted(true);
        userRepository.save(findUser.get());

        return new ResponseData(200, "Successfully deleted user", null);
    }
}
