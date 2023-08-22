package com.aldiramdan.library.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    @JsonProperty("old_password")
    @Size(min = 8, message = "password must be minimun 8 characters")
    @NotBlank(message = "old password is required")
    private String oldPassword;

    @JsonProperty("new_password")
    @Size(min = 8, message = "password must be minimun 8 characters")
    @NotBlank(message = "new password is required")
    private String newPassword;

    @JsonProperty("confirm_password")
    @Size(min = 8, message = "password must be minimun 8 characters")
    @NotBlank(message = "confirm password is required")
    private String confirmPassword;
}
