package com.codeatlas.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codeatlas.backend.ai.GeminiService;
import com.codeatlas.backend.dto.GitHubRepositoryResponse;
import com.codeatlas.backend.dto.RepositoryRequest;
import com.codeatlas.backend.dto.RepositoryResponse;
import com.codeatlas.backend.github.GitHubContentService;
import com.codeatlas.backend.github.GitHubService;
import com.codeatlas.backend.scanner.RepositoryScanner;
import com.codeatlas.backend.scanner.ScannerResult;

@Service
public class RepositoryService {
    @Autowired
private GitHubService gitHubService;
@Autowired
private GeminiService geminiService;
@Autowired
private GitHubContentService gitHubContentService;
private final RepositoryScanner repositoryScanner = new RepositoryScanner();
    public RepositoryResponse analyzeRepository(RepositoryRequest request) {

        String url = request.getUrl();

        if (url == null || !url.startsWith("https://github.com/")) {
    return new RepositoryResponse("", "", false, "", 0);
}

        String repositoryPath = url.replace("https://github.com/", "");
        String[] parts = repositoryPath.split("/");

        if (parts.length < 2) {
    return new RepositoryResponse("", "", false, "", 0);
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
        String pomContent =
        gitHubContentService.getPomFile(owner, repository);

if (pomContent.contains("spring-boot")) {
    result.setSpringBootProject(true);
}
        System.out.println("Before Gemini");

String summary = geminiService.generateSummary(result);
String recommendations =
        geminiService.generateRecommendations(result);
int score = 0;

if (result.isJavaProject()) score += 15;
if (result.isMavenProject()) score += 15;
if (result.isGradleProject()) score += 10;
if (result.isDockerProject()) score += 20;
if (result.isHasTests()) score += 25;
if (summary != null && !summary.isBlank()) score += 15;
System.out.println("After Gemini");

System.out.println(summary);


System.out.println("\n===== AI SUMMARY =====");
System.out.println(summary);
System.out.println("======================\n");
System.out.println("Java Project  : " + result.isJavaProject());
System.out.println("Maven Project : " + result.isMavenProject());
System.out.println("Gradle Project: " + result.isGradleProject());
System.out.println("Docker        : " + result.isDockerProject());
System.out.println("Has Tests     : " + result.isHasTests());

RepositoryResponse repositoryResponse =
        new RepositoryResponse(owner, repository, true, summary, score);

repositoryResponse.setLanguage(githubRepo.getLanguage());
repositoryResponse.setStars(githubRepo.getStargazers_count());
repositoryResponse.setForks(githubRepo.getForks_count());
repositoryResponse.setWatchers(githubRepo.getWatchers_count());
repositoryResponse.setOpenIssues(githubRepo.getOpen_issues_count());
repositoryResponse.setLastUpdated(githubRepo.getUpdated_at());
repositoryResponse.setOwnerAvatar(githubRepo.getOwner().getAvatarUrl());
repositoryResponse.setJavaProject(result.isJavaProject());
repositoryResponse.setMavenProject(result.isMavenProject());
repositoryResponse.setGradleProject(result.isGradleProject());
repositoryResponse.setDockerProject(result.isDockerProject());
repositoryResponse.setHasTests(result.isHasTests());
repositoryResponse.setAiRecommendations(recommendations);
repositoryResponse.setSpringBootProject(result.isSpringBootProject());
return repositoryResponse;
    }
}