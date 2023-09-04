package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.CategoryRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    private ResponseData responseData;

    @GetMapping
    public ResponseEntity<ResponseData> getAll() {
        responseData = categoryService.getAll();
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getById(@PathVariable Long id) throws Exception {
        responseData = categoryService.getById(id);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PostMapping
    public ResponseEntity<ResponseData> create(@Valid @RequestBody CategoryRequest request) throws Exception {
        responseData = categoryService.add(request);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) throws Exception {
        responseData = categoryService.update(id, request);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData> delete(@PathVariable Long id) throws Exception {
        responseData = categoryService.delete(id);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseData> recovery(@PathVariable Long id) throws Exception {
        responseData = categoryService.recovery(id);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }
}
