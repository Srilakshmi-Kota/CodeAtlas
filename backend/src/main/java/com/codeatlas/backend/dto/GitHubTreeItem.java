package com.codeatlas.backend.dto;

public class GitHubTreeItem {

    private String path;
    private String type;

    public GitHubTreeItem() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}