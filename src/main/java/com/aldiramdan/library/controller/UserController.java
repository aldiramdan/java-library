package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.ChangePasswordRequest;
import com.aldiramdan.library.model.dto.request.UserRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.entity.User;
import com.aldiramdan.library.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private ResponseData responseData;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> getAll() {
        responseData = userService.getAll();
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> getById(@PathVariable Long id) throws Exception {
        responseData = userService.getById(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseData> profile(@AuthenticationPrincipal User user) throws Exception {
        responseData = userService.getByUsername(user.getId());
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseData> update(@AuthenticationPrincipal User user, @Valid @RequestBody UserRequest request) throws Exception {
        responseData = userService.update(user.getId(), request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PatchMapping("/change-password")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseData> changePassword(@AuthenticationPrincipal User user, @Valid @RequestBody ChangePasswordRequest request) throws Exception {
        responseData = userService.changePassword(user.getId(), request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseData> delete(@AuthenticationPrincipal User user) throws Exception {
        responseData = userService.delete(user.getId());
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }
}
