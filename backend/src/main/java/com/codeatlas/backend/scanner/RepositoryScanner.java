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
}