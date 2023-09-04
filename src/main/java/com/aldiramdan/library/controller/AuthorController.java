package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.AuthorRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    private ResponseData responseData;

    @GetMapping
    public ResponseEntity<ResponseData> getAll() {
        responseData = authorService.getAll();
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getById(@PathVariable Long id) throws Exception {
        responseData = authorService.getById(id);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PostMapping
    public ResponseEntity<ResponseData> create(@Valid @RequestBody AuthorRequest request) throws Exception {
        responseData = authorService.add(request);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> update(@PathVariable Long id, @Valid @RequestBody AuthorRequest request) throws Exception {
        responseData = authorService.update(id, request);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData> delete(@PathVariable Long id) throws Exception {
        responseData = authorService.delete(id);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseData> recovery(@PathVariable Long id) throws Exception {
        responseData = authorService.recovery(id);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }
}
