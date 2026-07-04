package com.codeatlas.backend.service;

import org.springframework.stereotype.Service;

import com.codeatlas.backend.dto.RepositoryRequest;
import com.codeatlas.backend.dto.RepositoryResponse;

@Service
public class RepositoryService {

    public RepositoryResponse analyzeRepository(RepositoryRequest request) {

        String url = request.getUrl();

        if (url == null || !url.startsWith("https://github.com/")) {
            return new RepositoryResponse("", "", false);
        }

        String repositoryPath = url.replace("https://github.com/", "");

        String[] parts = repositoryPath.split("/");

        if (parts.length < 2) {
            return new RepositoryResponse("", "", false);
        }

        String owner = parts[0];
        String repository = parts[1];

        return new RepositoryResponse(owner, repository, true);
    }
}