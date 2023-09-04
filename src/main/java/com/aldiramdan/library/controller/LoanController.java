package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.LoanRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.entity.User;
import com.aldiramdan.library.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    private ResponseData responseData;

    @GetMapping
    public ResponseEntity<ResponseData> getAll() {
        responseData = loanService.getAll();
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getById(@PathVariable Long id) throws Exception {
        responseData = loanService.getById(id);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PostMapping
    public ResponseEntity<ResponseData> create(@AuthenticationPrincipal User user, @Valid @RequestBody LoanRequest request) throws Exception {
        request.setUser(user.getId());
        responseData = loanService.add(request);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> update(@PathVariable Long id, @AuthenticationPrincipal User user, @Valid @RequestBody LoanRequest request) throws Exception {
        request.setUser(user.getId());
        responseData = loanService.update(id, request);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<ResponseData> updateStatus(@PathVariable Long id, @Valid @RequestBody LoanRequest request) throws Exception {
        responseData = loanService.updateStatus(id, request);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData> delete(@PathVariable Long id) throws Exception {
        responseData = loanService.delete(id);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseData> recovery(@PathVariable Long id) throws Exception {
        responseData = loanService.recovery(id);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }
}
