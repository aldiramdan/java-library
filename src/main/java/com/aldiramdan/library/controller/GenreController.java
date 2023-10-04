package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.GenreRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<ResponseData> getAll() {
        ResponseData responseData = genreService.getAll();
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getById(@PathVariable Long id) {
        ResponseData responseData = genreService.getById(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> create(@Valid @RequestBody GenreRequest request) {
        ResponseData responseData = genreService.add(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> update(@PathVariable Long id, @Valid @RequestBody GenreRequest request) {
        ResponseData responseData = genreService.update(id, request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> delete(@PathVariable Long id) {
        ResponseData responseData = genreService.delete(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> recovery(@PathVariable Long id) {
        ResponseData responseData = genreService.recovery(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }
}
