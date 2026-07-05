package com.codeatlas.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codeatlas.backend.dto.GitHubRepositoryResponse;
import com.codeatlas.backend.dto.RepositoryRequest;
import com.codeatlas.backend.dto.RepositoryResponse;
import com.codeatlas.backend.github.GitHubService;
import com.codeatlas.backend.scanner.RepositoryScanner;
import com.codeatlas.backend.scanner.ScannerResult;

@Service
public class RepositoryService {
    @Autowired
private GitHubService gitHubService;

private final RepositoryScanner repositoryScanner = new RepositoryScanner();
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

        String githubApi = "https://api.github.com/repos/" + owner + "/" + repository;

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GitHubRepositoryResponse> response =
                restTemplate.getForEntity(
                        githubApi,
                        GitHubRepositoryResponse.class);

        GitHubRepositoryResponse githubRepo = response.getBody();

        System.out.println("Repository Name : " + githubRepo.getName());
        System.out.println("Language        : " + githubRepo.getLanguage());
        System.out.println("Stars           : " + githubRepo.getStargazers_count());

        String[] files = gitHubService.getRepositoryFiles(owner, repository);

ScannerResult result =
        repositoryScanner.scan(java.util.Arrays.asList(files));

System.out.println("Java Project  : " + result.isJavaProject());
System.out.println("Maven Project : " + result.isMavenProject());
System.out.println("Gradle Project: " + result.isGradleProject());
System.out.println("Docker        : " + result.isDockerProject());
System.out.println("Has Tests     : " + result.isHasTests());

return new RepositoryResponse(owner, repository, true);
    }
}