package com.codeatlas.backend.github;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codeatlas.backend.dto.GitHubRepositoryResponse;

@Service
public class GitHubService {

    private final RestTemplate restTemplate = new RestTemplate();

    public GitHubRepositoryResponse getRepository(String owner, String repository) {

        String url = "https://api.github.com/repos/" + owner + "/" + repository;

        ResponseEntity<GitHubRepositoryResponse> response =
                restTemplate.getForEntity(url, GitHubRepositoryResponse.class);

        return response.getBody();
    }
    public String[] getRepositoryFiles(String owner, String repository) {

    String url = "https://api.github.com/repos/" + owner + "/" + repository + "/contents";

    ResponseEntity<Object[]> response =
            restTemplate.getForEntity(url, Object[].class);

    Object[] files = response.getBody();

    String[] fileNames = new String[files.length];

    for (int i = 0; i < files.length; i++) {

        @SuppressWarnings("unchecked")
        java.util.LinkedHashMap<String, Object> file =
                (java.util.LinkedHashMap<String, Object>) files[i];

        fileNames[i] = (String) file.get("name");
        System.out.println(fileNames[i]);
    }

    return fileNames;
}
}