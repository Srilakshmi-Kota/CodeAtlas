package com.codeatlas.backend.github;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubContentService {

    @Value("${github.token}")
    private String githubToken;

    private final RestTemplate restTemplate = new RestTemplate();

    /*
     * Creates authenticated headers for GitHub API requests.
     */
    private HttpEntity<Void> createGitHubEntity() {

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(githubToken);

        // Ask GitHub to return the actual raw file content
        headers.set(
                HttpHeaders.ACCEPT,
                "application/vnd.github.raw+json"
        );

        headers.set(
                "X-GitHub-Api-Version",
                "2022-11-28"
        );

        headers.set(
                HttpHeaders.USER_AGENT,
                "CodeAtlas"
        );

        return new HttpEntity<>(headers);
    }

    /*
     * Fetch a file directly from the repository.
     *
     * No hard-coded "main" or "master" branch is required.
     * GitHub automatically uses the repository's default branch.
     */
    public String getFileContent(
            String owner,
            String repository,
            String path) {

        String url =
                "https://api.github.com/repos/"
                        + owner
                        + "/"
                        + repository
                        + "/contents/"
                        + path;

        try {

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            createGitHubEntity(),
                            String.class
                    );

            return response.getBody() != null
                    ? response.getBody()
                    : "";

        } catch (Exception e) {

            System.out.println(
                    "Could not fetch file: "
                            + path
                            + " from "
                            + owner
                            + "/"
                            + repository
            );

            return "";
        }
    }

    public String getPomFile(
            String owner,
            String repository) {

        return getFileContent(
                owner,
                repository,
                "pom.xml"
        );
    }

    public String getBuildGradleFile(
            String owner,
            String repository) {

        return getFileContent(
                owner,
                repository,
                "build.gradle"
        );
    }
}