package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.BookRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ResponseData> getAll(@RequestParam(defaultValue = "") Boolean status) {
        ResponseData responseData = bookService.getAll(status);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getById(@PathVariable Long id) throws Exception {
        ResponseData responseData = bookService.getById(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @GetMapping("/search/{column}")
    public ResponseEntity<ResponseData> searchByName(@PathVariable String column, @RequestParam String name) {
        ResponseData responseData = bookService.searchByName(column, name);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> create(@Valid @RequestBody BookRequest request) throws Exception {
        ResponseData responseData = bookService.add(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> update(@PathVariable Long id, @Valid @RequestBody BookRequest request) throws Exception {
        ResponseData responseData = bookService.update(id, request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> delete(@PathVariable Long id) throws Exception {
        ResponseData responseData = bookService.delete(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> recovery(@PathVariable Long id) throws Exception {
        ResponseData responseData = bookService.recovery(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }
}
