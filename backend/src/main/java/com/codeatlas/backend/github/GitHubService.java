package com.codeatlas.backend.github;

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
}