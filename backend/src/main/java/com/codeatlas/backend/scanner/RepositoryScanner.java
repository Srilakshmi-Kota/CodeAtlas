package com.codeatlas.backend.scanner;

import java.util.List;

public class RepositoryScanner {

    public ScannerResult scan(List<String> fileNames) {

        ScannerResult result = new ScannerResult();

        for (String file : fileNames) {

            if (file == null) {
                continue;
            }

            String normalizedFile = file
                    .replace("\\", "/")
                    .toLowerCase();

            // JAVA
            if (normalizedFile.endsWith(".java")) {
                result.setJavaProject(true);
            }

            // MAVEN
            if (normalizedFile.equals("pom.xml")
                    || normalizedFile.endsWith("/pom.xml")) {

                result.setJavaProject(true);
                result.setMavenProject(true);
            }

            // GRADLE
            if (normalizedFile.equals("build.gradle")
                    || normalizedFile.equals("build.gradle.kts")
                    || normalizedFile.endsWith("/build.gradle")
                    || normalizedFile.endsWith("/build.gradle.kts")) {

                result.setJavaProject(true);
                result.setGradleProject(true);
            }

            // DOCKER
            if (normalizedFile.equals("dockerfile")
                    || normalizedFile.endsWith("/dockerfile")
                    || normalizedFile.equals("docker-compose.yml")
                    || normalizedFile.equals("docker-compose.yaml")
                    || normalizedFile.endsWith("/docker-compose.yml")
                    || normalizedFile.endsWith("/docker-compose.yaml")) {

                result.setDockerProject(true);
            }

            // TESTS
            if (normalizedFile.startsWith("src/test/")
                    || normalizedFile.contains("/src/test/")
                    || normalizedFile.startsWith("test/")
                    || normalizedFile.startsWith("tests/")) {

                result.setHasTests(true);
            }

            // SPRING BOOT SIGNALS
            if (normalizedFile.endsWith("application.yml")
                    || normalizedFile.endsWith("application.yaml")
                    || normalizedFile.endsWith("application.properties")) {

                result.setSpringBootProject(true);
            }

            // README
            if (normalizedFile.equals("readme")
                    || normalizedFile.equals("readme.md")
                    || normalizedFile.equals("readme.txt")
                    || normalizedFile.equals("readme.rst")) {

                result.setHasReadme(true);
            }

            // CI/CD
            if (normalizedFile.startsWith(".github/workflows/")
                    && (normalizedFile.endsWith(".yml")
                    || normalizedFile.endsWith(".yaml"))) {

                result.setHasCiCd(true);
            }

            if (normalizedFile.equals(".gitlab-ci.yml")
                    || normalizedFile.endsWith("/jenkinsfile")
                    || normalizedFile.equals("jenkinsfile")
                    || normalizedFile.startsWith(".circleci/")) {

                result.setHasCiCd(true);
            }

            // DOCUMENTATION
            if (normalizedFile.startsWith("docs/")
                    || normalizedFile.contains("/docs/")
                    || normalizedFile.startsWith("documentation/")
                    || normalizedFile.contains("/documentation/")) {

                result.setHasDocumentation(true);
            }

            // LICENSE
            String fileNameOnly =
                    normalizedFile.contains("/")
                            ? normalizedFile.substring(
                                    normalizedFile.lastIndexOf("/") + 1)
                            : normalizedFile;

            if (fileNameOnly.equals("license")
                    || fileNameOnly.equals("license.md")
                    || fileNameOnly.equals("license.txt")
                    || fileNameOnly.equals("licence")
                    || fileNameOnly.equals("licence.md")
                    || fileNameOnly.equals("licence.txt")) {

                result.setHasLicense(true);
            }
        }

        return result;
    }
}