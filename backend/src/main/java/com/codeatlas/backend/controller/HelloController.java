package com.codeatlas.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeatlas.backend.dto.RepositoryRequest;
import com.codeatlas.backend.dto.RepositoryResponse;
import com.codeatlas.backend.service.RepositoryService;

@RestController
@RequestMapping("/api/repository")
public class HelloController {

    @Autowired
    private RepositoryService repositoryService;

    @PostMapping("/analyze")
    public RepositoryResponse analyzeRepository(@RequestBody RepositoryRequest request) {

        return repositoryService.analyzeRepository(request);

    }
}