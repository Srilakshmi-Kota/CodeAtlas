package com.codeatlas.backend.scanner;

import java.util.List;

public class RepositoryScanner {

    public ScannerResult scan(List<String> fileNames) {

        ScannerResult result = new ScannerResult();

        int javaFileCount = 0;
        int controllerCount = 0;
        int serviceCount = 0;
        int repositoryCount = 0;
        int entityCount = 0;
        int configurationCount = 0;
        int testFileCount = 0;

        for (String file : fileNames) {

            if (file == null) {
                continue;
            }

            // Normalize path so detection works consistently
            String normalizedPath = file.replace("\\", "/");
            String lowerPath = normalizedPath.toLowerCase();

            // -----------------------------
            // Build system detection
            // -----------------------------

            if (lowerPath.endsWith("pom.xml")) {
                result.setJavaProject(true);
                result.setMavenProject(true);
            }

            if (lowerPath.endsWith("build.gradle")
                    || lowerPath.endsWith("build.gradle.kts")) {

                result.setJavaProject(true);
                result.setGradleProject(true);
            }

            // -----------------------------
            // Docker detection
            // -----------------------------

            if (lowerPath.endsWith("dockerfile")
                    || lowerPath.endsWith("docker-compose.yml")
                    || lowerPath.endsWith("docker-compose.yaml")) {

                result.setDockerProject(true);
            }

            // -----------------------------
            // Spring Boot configuration
            // -----------------------------

            if (lowerPath.endsWith("application.yml")
                    || lowerPath.endsWith("application.yaml")
                    || lowerPath.endsWith("application.properties")) {

                result.setSpringBootProject(true);
            }

            // -----------------------------
            // README detection
            // -----------------------------

            if (lowerPath.endsWith("readme.md")
                    || lowerPath.endsWith("readme")) {

                result.setHasReadme(true);
            }

            // -----------------------------
            // License detection
            // -----------------------------

            if (lowerPath.endsWith("license")
                    || lowerPath.endsWith("license.md")
                    || lowerPath.endsWith("license.txt")) {

                result.setHasLicense(true);
            }

            // -----------------------------
            // CI/CD detection
            // -----------------------------

            if (lowerPath.contains(".github/workflows/")
                    || lowerPath.endsWith("jenkinsfile")
                    || lowerPath.endsWith(".gitlab-ci.yml")) {

                result.setHasCiCd(true);
            }

            // -----------------------------
            // Documentation detection
            // -----------------------------

            if (lowerPath.startsWith("docs/")
                    || lowerPath.contains("/docs/")) {

                result.setHasDocumentation(true);
            }

            // -----------------------------
            // Java file analysis
            // -----------------------------

            if (lowerPath.endsWith(".java")) {

                result.setJavaProject(true);
                javaFileCount++;

                // Test files
                if (lowerPath.contains("src/test/")
                        || lowerPath.contains("/test/")
                        || lowerPath.endsWith("test.java")
                        || lowerPath.endsWith("tests.java")) {

                    testFileCount++;
                    result.setHasTests(true);
                }

                // Controller detection
                if (lowerPath.contains("/controller/")
                        || lowerPath.endsWith("controller.java")) {

                    controllerCount++;
                }

                // Service detection
                if (lowerPath.contains("/service/")
                        || lowerPath.endsWith("service.java")
                        || lowerPath.endsWith("serviceimpl.java")) {

                    serviceCount++;
                }

                // Repository detection
                if (lowerPath.contains("/repository/")
                        || lowerPath.endsWith("repository.java")
                        || lowerPath.endsWith("dao.java")) {

                    repositoryCount++;
                }

                // Entity / Model detection
                if (lowerPath.contains("/entity/")
                        || lowerPath.contains("/model/")
                        || lowerPath.endsWith("entity.java")) {

                    entityCount++;
                }

                // Configuration detection
                if (lowerPath.contains("/config/")
                        || lowerPath.contains("/configuration/")
                        || lowerPath.endsWith("config.java")
                        || lowerPath.endsWith("configuration.java")) {

                    configurationCount++;
                }
            }
        }

        // Store calculated metrics

        result.setJavaFileCount(javaFileCount);
        result.setControllerCount(controllerCount);
        result.setServiceCount(serviceCount);
        result.setRepositoryCount(repositoryCount);
        result.setEntityCount(entityCount);
        result.setConfigurationCount(configurationCount);
        result.setTestFileCount(testFileCount);

        return result;
    }
    public void scanEndpoints(
        List<String> controllerContents,
        ScannerResult result) {

    if (controllerContents == null || result == null) {
        return;
    }

    int getCount = 0;
    int postCount = 0;
    int putCount = 0;
    int deleteCount = 0;
    int patchCount = 0;

    List<ApiEndpoint> endpoints =
            new java.util.ArrayList<>();

    for (String content : controllerContents) {

        if (content == null || content.isBlank()) {
            continue;
        }

        /*
         * Detect controller-level base path.
         *
         * Example:
         * @RequestMapping("/owners/{ownerId}")
         */
        String basePath =
                extractControllerBasePath(content);

        // Preserve our already-working endpoint counts
        getCount +=
                countOccurrences(content, "@GetMapping");

        postCount +=
                countOccurrences(content, "@PostMapping");

        putCount +=
                countOccurrences(content, "@PutMapping");

        deleteCount +=
                countOccurrences(content, "@DeleteMapping");

        patchCount +=
                countOccurrences(content, "@PatchMapping");

        /*
         * Extract method-level mappings and combine
         * them with the controller base path.
         */
        extractEndpoints(
                content,
                "@GetMapping",
                "GET",
                basePath,
                endpoints
        );

        extractEndpoints(
                content,
                "@PostMapping",
                "POST",
                basePath,
                endpoints
        );

        extractEndpoints(
                content,
                "@PutMapping",
                "PUT",
                basePath,
                endpoints
        );

        extractEndpoints(
                content,
                "@DeleteMapping",
                "DELETE",
                basePath,
                endpoints
        );

        extractEndpoints(
                content,
                "@PatchMapping",
                "PATCH",
                basePath,
                endpoints
        );
    }

    result.setGetEndpointCount(getCount);
    result.setPostEndpointCount(postCount);
    result.setPutEndpointCount(putCount);
    result.setDeleteEndpointCount(deleteCount);
    result.setPatchEndpointCount(patchCount);

    result.setApiEndpoints(endpoints);
}
private void extractEndpoints(
        String content,
        String annotation,
        String httpMethod,
        String basePath,
        List<ApiEndpoint> endpoints) {

    java.util.regex.Pattern pattern =
            java.util.regex.Pattern.compile(
                    java.util.regex.Pattern.quote(annotation)
                            + "\\s*(?:\\(\\s*(?:value\\s*=\\s*)?"
                            + "[\"']([^\"']*)[\"'][^)]*\\))?"
            );

    java.util.regex.Matcher matcher =
            pattern.matcher(content);

    while (matcher.find()) {

        String methodPath = matcher.group(1);

        if (methodPath == null) {
            methodPath = "";
        }

        String fullPath =
                combinePaths(
                        basePath,
                        methodPath
                );

        endpoints.add(
                new ApiEndpoint(
                        httpMethod,
                        fullPath
                )
        );
    }
}

private String extractControllerBasePath(
        String content) {

    /*
     * Search only before the class declaration.
     * This prevents method-level @RequestMapping
     * annotations from being mistaken for the
     * controller base path.
     */

    int classIndex =
            content.indexOf("class ");

    String controllerHeader =
            classIndex >= 0
                    ? content.substring(0, classIndex)
                    : content;

    java.util.regex.Pattern pattern =
            java.util.regex.Pattern.compile(
                    "@RequestMapping\\s*"
                            + "\\(\\s*"
                            + "(?:value\\s*=\\s*)?"
                            + "[\"']([^\"']*)[\"']"
            );

    java.util.regex.Matcher matcher =
            pattern.matcher(controllerHeader);

    if (matcher.find()) {

        String path = matcher.group(1);

        if (path != null && !path.isBlank()) {
            return path.trim();
        }
    }

    return "";
}


private String combinePaths(
        String basePath,
        String methodPath) {

    String base =
            basePath == null
                    ? ""
                    : basePath.trim();

    String method =
            methodPath == null
                    ? ""
                    : methodPath.trim();

    if (!base.isEmpty()
            && !base.startsWith("/")) {

        base = "/" + base;
    }

    if (base.endsWith("/")
            && base.length() > 1) {

        base =
                base.substring(
                        0,
                        base.length() - 1
                );
    }

    if (!method.isEmpty()
            && !method.startsWith("/")) {

        method = "/" + method;
    }

    String fullPath = base + method;

    if (fullPath.isBlank()) {
        return "/";
    }

    return fullPath;
}

private int countOccurrences(String text, String target) {

    int count = 0;
    int index = 0;

    while ((index = text.indexOf(target, index)) != -1) {
        count++;
        index += target.length();
    }

    return count;
}
}