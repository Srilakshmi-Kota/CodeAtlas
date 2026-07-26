package com.codeatlas.backend.github;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codeatlas.backend.dto.GitHubRepositoryResponse;
import com.codeatlas.backend.dto.GitHubTreeItem;
import com.codeatlas.backend.dto.GitHubTreeResponse;

@Service
public class GitHubService {

    @Value("${github.token}")
    private String githubToken;

    private final RestTemplate restTemplate = new RestTemplate();

    /*
     * Creates authenticated headers for every GitHub API request.
     */
    private HttpEntity<Void> createGitHubEntity() {

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

        return new HttpEntity<>(headers);
    }

    /*
     * Fetch repository metadata.
     */
    public GitHubRepositoryResponse getRepository(
            String owner,
            String repository) {

        String url =
                "https://api.github.com/repos/"
                        + owner
                        + "/"
                        + repository;

        ResponseEntity<GitHubRepositoryResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        createGitHubEntity(),
                        GitHubRepositoryResponse.class
                );

        return response.getBody();
    }

    /*
     * Fetch files/directories from repository root.
     */
    public String[] getRepositoryFiles(
        String owner,
        String repository) {

    /*
     * First get repository metadata so we know
     * the actual default branch.
     */
    GitHubRepositoryResponse repositoryInfo =
            getRepository(owner, repository);

    if (repositoryInfo == null
            || repositoryInfo.getDefault_branch() == null) {

        return new String[0];
    }

    String defaultBranch =
            repositoryInfo.getDefault_branch();

    /*
     * Git Trees API recursively retrieves nested
     * repository paths.
     */
    String url =
            "https://api.github.com/repos/"
                    + owner
                    + "/"
                    + repository
                    + "/git/trees/"
                    + defaultBranch
                    + "?recursive=1";

    ResponseEntity<GitHubTreeResponse> response =
            restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    createGitHubEntity(),
                    GitHubTreeResponse.class
            );

    GitHubTreeResponse treeResponse =
            response.getBody();

    if (treeResponse == null
            || treeResponse.getTree() == null) {

        return new String[0];
    }

    return treeResponse
            .getTree()
            .stream()
            .map(GitHubTreeItem::getPath)
            .toArray(String[]::new);
}
public List<String> getControllerContents(
        String owner,
        String repository) {

    List<String> controllerContents = new ArrayList<>();

    String[] files = getRepositoryFiles(owner, repository);

    for (String file : files) {

        if (file == null) {
            continue;
        }

        String normalizedPath = file.replace("\\", "/");
        String lowerPath = normalizedPath.toLowerCase();

        if (!lowerPath.endsWith(".java")) {
            continue;
        }

        if (!lowerPath.contains("/controller/")
                && !lowerPath.endsWith("controller.java")) {
            continue;
        }

        try {

            String url =
                    "https://api.github.com/repos/"
                            + owner
                            + "/"
                            + repository
                            + "/contents/"
                            + normalizedPath;

            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(githubToken);
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

            HttpEntity<Void> entity =
                    new HttpEntity<>(headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            String.class
                    );

            String content = response.getBody();

            if (content != null && !content.isBlank()) {
                controllerContents.add(content);
            }

        } catch (Exception e) {

            System.out.println(
                    "Could not fetch controller: "
                            + normalizedPath
            );
        }
    }

    return controllerContents;
}
}