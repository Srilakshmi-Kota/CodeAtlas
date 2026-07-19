package com.codeatlas.backend.dto;

import java.util.List;

public class GitHubTreeResponse {

    private List<GitHubTreeItem> tree;
    private boolean truncated;

    public GitHubTreeResponse() {
    }

    public List<GitHubTreeItem> getTree() {
        return tree;
    }

    public void setTree(List<GitHubTreeItem> tree) {
        this.tree = tree;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }
}