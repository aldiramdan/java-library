package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.entity.User;
import com.aldiramdan.library.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    private ResponseData responseData;

    @GetMapping
    public ResponseEntity<ResponseData> getAll(@AuthenticationPrincipal User user) throws Exception {
        responseData = historyService.getById(user.getId());
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }
}
