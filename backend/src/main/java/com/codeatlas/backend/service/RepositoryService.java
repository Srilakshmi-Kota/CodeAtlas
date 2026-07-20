package com.codeatlas.backend.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

    @Value("${github.token}")
    private String githubToken;

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private GitHubContentService gitHubContentService;

    private final RepositoryScanner repositoryScanner =
            new RepositoryScanner();

    public RepositoryResponse analyzeRepository(RepositoryRequest request) {

        String url = request.getUrl();

        // Validate GitHub URL
        if (url == null || !url.startsWith("https://github.com/")) {
            return new RepositoryResponse("", "", false, "", 0);
        }

        String repositoryPath =
                url.replace("https://github.com/", "");

        String[] parts = repositoryPath.split("/");

        if (parts.length < 2) {
            return new RepositoryResponse("", "", false, "", 0);
        }

        String owner = parts[0];
        String repository = parts[1];

        /*
         * ==========================================
         * FETCH REPOSITORY METADATA FROM GITHUB API
         * ==========================================
         */

        String githubApi =
                "https://api.github.com/repos/"
                        + owner
                        + "/"
                        + repository;

        RestTemplate restTemplate = new RestTemplate();

        // Add GitHub authentication
        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(githubToken);

        headers.set(
                HttpHeaders.ACCEPT,
                "application/vnd.github+json"
        );

        headers.set(
                "X-GitHub-Api-Version",
                "2022-11-28"
        );

        headers.set(
                HttpHeaders.USER_AGENT,
                "CodeAtlas"
        );

        HttpEntity<Void> entity =
                new HttpEntity<>(headers);

        ResponseEntity<GitHubRepositoryResponse> response =
                restTemplate.exchange(
                        githubApi,
                        HttpMethod.GET,
                        entity,
                        GitHubRepositoryResponse.class
                );

        GitHubRepositoryResponse githubRepo =
                response.getBody();

        if (githubRepo == null) {
            return new RepositoryResponse(
                    owner,
                    repository,
                    false,
                    "Unable to retrieve repository information.",
                    0
            );
        }

        System.out.println(
                "Repository Name : "
                        + githubRepo.getName()
        );

        System.out.println(
                "Language        : "
                        + githubRepo.getLanguage()
        );

        System.out.println(
                "Stars           : "
                        + githubRepo.getStargazers_count()
        );

        /*
         * ==============================
         * FETCH REPOSITORY FILE STRUCTURE
         * ==============================
         */

        String[] files =
                gitHubService.getRepositoryFiles(
                        owner,
                        repository
                );

        ScannerResult result =
                repositoryScanner.scan(
                        Arrays.asList(files)
                );

        /*
         * ==============================
         * CHECK POM.XML FOR SPRING BOOT
         * ==============================
         */

        String pomContent =
                gitHubContentService.getPomFile(
                        owner,
                        repository
                );

        if (pomContent != null
                && pomContent.contains("spring-boot")) {

            result.setSpringBootProject(true);
        }

        /*
         * ==============================
         * GENERATE AI ANALYSIS
         * ==============================
         */

        System.out.println("Before Gemini");

        String summary =
                geminiService.generateSummary(result);

        String recommendations =
                geminiService.generateRecommendations(result);

        /*
         * ==============================
         * CALCULATE REPOSITORY HEALTH
         * ==============================
         */

        int score = 0;

// Project structure
if (result.isJavaProject()) {
    score += 10;
}

// Build system - reward once even if both Maven and Gradle exist
if (result.isMavenProject() || result.isGradleProject()) {
    score += 10;
}

// Testing
if (result.isHasTests()) {
    score += 20;
}

// Documentation and maintainability
if (result.isHasReadme()) {
    score += 10;
}

if (result.isHasDocumentation()) {
    score += 10;
}

if (result.isHasLicense()) {
    score += 5;
}

// DevOps
if (result.isHasCiCd()) {
    score += 15;
}

if (result.isDockerProject()) {
    score += 10;
}

// Framework/project configuration
if (result.isSpringBootProject()) {
    score += 10;
}

score = Math.min(score, 100);

        System.out.println("After Gemini");

        System.out.println(
                "\n===== AI SUMMARY ====="
        );

        System.out.println(summary);

        System.out.println(
                "======================\n"
        );

        System.out.println(
                "Java Project  : "
                        + result.isJavaProject()
        );

        System.out.println(
                "Maven Project : "
                        + result.isMavenProject()
        );

        System.out.println(
                "Gradle Project: "
                        + result.isGradleProject()
        );

        System.out.println(
                "Docker        : "
                        + result.isDockerProject()
        );

        System.out.println(
                "Has Tests     : "
                        + result.isHasTests()
        );

        /*
         * ==============================
         * BUILD FINAL RESPONSE
         * ==============================
         */

        RepositoryResponse repositoryResponse =
                new RepositoryResponse(
                        owner,
                        repository,
                        true,
                        summary,
                        score
                );

        repositoryResponse.setLanguage(
                githubRepo.getLanguage()
        );

        repositoryResponse.setStars(
                githubRepo.getStargazers_count()
        );

        repositoryResponse.setForks(
                githubRepo.getForks_count()
        );

        repositoryResponse.setWatchers(
                githubRepo.getWatchers_count()
        );

        repositoryResponse.setOpenIssues(
                githubRepo.getOpen_issues_count()
        );

        repositoryResponse.setLastUpdated(
                githubRepo.getUpdated_at()
        );

        if (githubRepo.getOwner() != null) {

            repositoryResponse.setOwnerAvatar(
                    githubRepo
                            .getOwner()
                            .getAvatarUrl()
            );
        }

        repositoryResponse.setJavaProject(
                result.isJavaProject()
        );

        repositoryResponse.setMavenProject(
                result.isMavenProject()
        );

        repositoryResponse.setGradleProject(
                result.isGradleProject()
        );

        repositoryResponse.setDockerProject(
                result.isDockerProject()
        );

        repositoryResponse.setHasTests(
                result.isHasTests()
        );

        repositoryResponse.setSpringBootProject(
                result.isSpringBootProject()
        );

        repositoryResponse.setHasReadme(
                result.isHasReadme()
        );

        repositoryResponse.setHasCiCd(
                result.isHasCiCd()
        );

        repositoryResponse.setHasDocumentation(
                result.isHasDocumentation()
        );

        repositoryResponse.setHasLicense(
                result.isHasLicense()
        );

        repositoryResponse.setAiRecommendations(
                recommendations
        );

        return repositoryResponse;
    }
}