package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.HistoryService;
import com.aldiramdan.library.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    private ResponseData responseData;

    @GetMapping
    public ResponseEntity<ResponseData> getAll(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        String username = userDetails.getUsername();
        responseData = historyService.getByUsername(username);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }
}
