package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.PublisherRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping
    public ResponseEntity<ResponseData> getAll() {
        ResponseData responseData = publisherService.getAll();
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getById(@PathVariable Long id) throws Exception {
        ResponseData responseData = publisherService.getById(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> create(@Valid @RequestBody PublisherRequest request) throws Exception {
        ResponseData responseData = publisherService.add(request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> update(@PathVariable Long id, @Valid @RequestBody PublisherRequest request) throws Exception {
        ResponseData responseData = publisherService.update(id, request);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> delete(@PathVariable Long id) throws Exception {
        ResponseData responseData = publisherService.delete(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseData> recovery(@PathVariable Long id) throws Exception {
        ResponseData responseData = publisherService.recovery(id);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }
}
