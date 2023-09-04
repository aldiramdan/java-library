package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.BookRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    private ResponseData responseData;

    @GetMapping
    public ResponseEntity<ResponseData> getAll(@RequestParam(defaultValue = "") Boolean status) {
        responseData = bookService.getAll(status);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getById(@PathVariable Long id) throws Exception {
        responseData = bookService.getById(id);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @GetMapping("/search/{column}")
    public ResponseEntity<ResponseData> searchByName(@PathVariable String column, @RequestParam String name) {
        responseData = bookService.searchByName(column, name);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PostMapping
    public ResponseEntity<ResponseData> create(@Valid @RequestBody BookRequest request) throws Exception {
        responseData = bookService.add(request);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> update(@PathVariable Long id, @Valid @RequestBody BookRequest request) throws Exception {
        responseData = bookService.update(id, request);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData> delete(@PathVariable Long id) throws Exception {
        responseData = bookService.delete(id);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseData> recovery(@PathVariable Long id) throws Exception {
        responseData = bookService.recovery(id);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }
}
