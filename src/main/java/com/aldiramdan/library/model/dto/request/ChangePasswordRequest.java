package com.aldiramdan.library.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @Size(min = 8, message = "password must be minimum 8 characters")
    @NotBlank(message = "old password is required")
    private String oldPassword;

    @Size(min = 8, message = "password must be minimum 8 characters")
    @NotBlank(message = "new password is required")
    private String newPassword;

    @Size(min = 8, message = "password must be minimum 8 characters")
    @NotBlank(message = "confirm password is required")
    private String confirmPassword;
}
