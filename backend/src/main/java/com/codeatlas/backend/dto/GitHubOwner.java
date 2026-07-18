package com.codeatlas.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubOwner {

    @JsonProperty("avatar_url")
    private String avatarUrl;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}