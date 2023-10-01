package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.CategoryRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ResponseData> getAll() {
        ResponseData responseData = categoryService.getAll();
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getById(@PathVariable Long id) throws Exception {
        ResponseData responseData = categoryService.getById(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> create(@Valid @RequestBody CategoryRequest request) throws Exception {
        ResponseData responseData = categoryService.add(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) throws Exception {
        ResponseData responseData = categoryService.update(id, request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> delete(@PathVariable Long id) throws Exception {
        ResponseData responseData = categoryService.delete(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> recovery(@PathVariable Long id) throws Exception {
        ResponseData responseData = categoryService.recovery(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }
}
