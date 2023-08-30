package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.ChangePasswordRequest;
import com.aldiramdan.library.model.dto.request.UserRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    private ResponseData responseData;

    @GetMapping
    public ResponseEntity<ResponseData> getAll() {
        responseData = userService.getAll();
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getById(@PathVariable Long id) throws Exception {
        responseData = userService.getById(id);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseData> profile(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        String username = userDetails.getUsername();
        responseData = userService.getByUsername(username);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseData> update(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UserRequest request) throws Exception {
        String username = userDetails.getUsername();
        responseData = userService.update(username, request);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ResponseData> changePassword(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody ChangePasswordRequest request) throws Exception {
        String username = userDetails.getUsername();
        responseData = userService.changePassword(username, request);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseData> delete(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        String username = userDetails.getUsername();
        responseData = userService.delete(username);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }
}
