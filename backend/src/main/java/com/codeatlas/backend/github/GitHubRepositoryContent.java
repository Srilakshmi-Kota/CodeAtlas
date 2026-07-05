package com.codeatlas.backend.github;

import java.util.List;

public class GitHubRepositoryContent {

    private List<GitHubFile> files;

    public GitHubRepositoryContent() {
    }

    public List<GitHubFile> getFiles() {
        return files;
    }

    public void setFiles(List<GitHubFile> files) {
        this.files = files;
    }
}