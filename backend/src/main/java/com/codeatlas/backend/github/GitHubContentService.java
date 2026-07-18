package com.codeatlas.backend.github;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubContentService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getFileContent(String owner, String repository, String path) {

        String rawUrl = "https://raw.githubusercontent.com/"
                + owner + "/"
                + repository + "/master/"
                + path;

        try {
            return restTemplate.getForObject(rawUrl, String.class);
        } catch (Exception e) {

            rawUrl = "https://raw.githubusercontent.com/"
                    + owner + "/"
                    + repository + "/main/"
                    + path;

            try {
                return restTemplate.getForObject(rawUrl, String.class);
            } catch (Exception ex) {
                return "";
            }
        }
        
    }
    public String getPomFile(String owner, String repository) {
    return getFileContent(owner, repository, "pom.xml");
}

public String getBuildGradleFile(String owner, String repository) {
    return getFileContent(owner, repository, "build.gradle");
}
}