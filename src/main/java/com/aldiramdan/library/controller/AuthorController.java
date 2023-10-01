package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.AuthorRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<ResponseData> getAll() {
        ResponseData responseData = authorService.getAll();
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getById(@PathVariable Long id) throws Exception {
        ResponseData responseData = authorService.getById(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> create(@Valid @RequestBody AuthorRequest request) throws Exception {
        ResponseData responseData = authorService.add(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> update(@PathVariable Long id, @Valid @RequestBody AuthorRequest request) throws Exception {
        ResponseData responseData = authorService.update(id, request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> delete(@PathVariable Long id) throws Exception {
        ResponseData responseData = authorService.delete(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> recovery(@PathVariable Long id) throws Exception {
        ResponseData responseData = authorService.recovery(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }
}
