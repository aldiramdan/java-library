package com.aldiramdan.library.validator;

import com.aldiramdan.library.exception.custom.BadRequestException;
import com.aldiramdan.library.exception.custom.ConflictException;
import com.aldiramdan.library.exception.custom.NotFoundException;
import com.aldiramdan.library.exception.custom.NotProcessException;
import com.aldiramdan.library.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserValidator {
    @Autowired
    PasswordEncoder passwordEncoder;

    public void validateUsernameIsExists(Optional<User> findByUsername) throws Exception {
        if (findByUsername.isPresent()) {
            throw new ConflictException("Username has been regitered!");
        }
    }

    public void validateEmailIsExists(Optional<User> findByEmail) throws Exception {
        if (findByEmail.isPresent()) {
            throw new ConflictException("Email has been regitered!");
        }
    }

    public void validateUserNotFound(Optional<User> findUser) throws Exception {
        if (findUser.isEmpty()) {
            throw new NotFoundException("User not found!, please register!");
        }
    }

    public void validateUserNotIsActives(Optional<User> findUser) throws Exception {
        if (Objects.isNull(findUser.get().getIsActives()) || !findUser.get().getIsActives()) {
            throw new IllegalAccessException("User not verification account!, please confirm account!");
        }
    }

    public void validateUserIsAlreadyDeleted(User user) throws Exception {
        if (Objects.nonNull(user.getIsDeleted()) && user.getIsDeleted()) {
            throw new NotProcessException("User is already deleted!");
        }
    }

    public void validateInvalidOldPassword(String oldPassword, String dbPassword) throws Exception {
        if (!passwordEncoder.matches(oldPassword, dbPassword)) {
            throw new BadRequestException("Old password is incorrect!");
        }
    }

    public void validateInvalidNewPassword(String newPassword, String confirmPassword) throws Exception {
        if (!newPassword.equals(confirmPassword)) {
            throw new BadRequestException("New password and Confirm password do not match!");
        }
    }

    public void validateUserCheckPasswordStrength(String password) throws Exception {
        boolean hasLower = false, hasUpper = false,
                hasDigit = false, specialChar = false;

        Set<Character> set = new HashSet<Character>(
                Arrays.asList('!', '@', '#', '$', '%', '^', '&',
                        '*', '(', ')', '-', '+'));

        for (char i : password.toCharArray())
        {
            if (Character.isLowerCase(i))
                hasLower = true;
            if (Character.isUpperCase(i))
                hasUpper = true;
            if (Character.isDigit(i))
                hasDigit = true;
            if (set.contains(i))
                specialChar = true;
        }

        if (!hasLower && !hasUpper && !specialChar && !hasDigit) {
            throw new BadRequestException("Password so week, please input upper, lower, digit and symbol in character");
        }
    }
}
