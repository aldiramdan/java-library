package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.entity.User;
import com.aldiramdan.library.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseData> getAll(@AuthenticationPrincipal User user) throws Exception {
        ResponseData responseData = historyService.getById(user.getId());
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }
}
