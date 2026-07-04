package com.codeatlas.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeatlas.backend.dto.RepositoryRequest;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public Map<String, String> hello() {

        Map<String, String> response = new HashMap<>();

        response.put("project", "CodeAtlas");
        response.put("status", "Running");
        response.put("version", "1.0");

        return response;
    }

    @PostMapping("/repository/analyze")
    public Map<String, String> analyze(@RequestBody RepositoryRequest request) {

        Map<String, String> response = new HashMap<>();

        response.put("repository", request.getUrl());
        response.put("status", "Received");
        response.put("message", "Repository analysis will start soon.");

        return response;
    }
}