package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.response.ResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public ResponseEntity<ResponseData> home() {
        Map<String, Object> mapResponse = new HashMap<>();
        mapResponse.put("repo", "");
        mapResponse.put("demo", "");
        mapResponse.put("docs", "");

        ResponseData responseData = new ResponseData(200, "Success", mapResponse);
        return ResponseEntity.status(responseData.getStatusCode()).body(responseData);
    }
}
