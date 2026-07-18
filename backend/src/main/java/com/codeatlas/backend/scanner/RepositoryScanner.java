package com.codeatlas.backend.scanner;

import java.util.List;

public class RepositoryScanner {

    public ScannerResult scan(List<String> fileNames) {

        ScannerResult result = new ScannerResult();

        for (String file : fileNames) {

            if (file.equalsIgnoreCase("pom.xml")) {
                result.setJavaProject(true);
                result.setMavenProject(true);
            }

            if (file.equalsIgnoreCase("build.gradle")
        || file.equalsIgnoreCase("build.gradle.kts")) {
    result.setJavaProject(true);
    result.setGradleProject(true);
}
            if (file.equalsIgnoreCase("Dockerfile")
        || file.equalsIgnoreCase("docker-compose.yml")
        || file.equalsIgnoreCase("docker-compose.yaml")) {

    result.setDockerProject(true);
}

            if (file.contains("src/test")) {
                result.setHasTests(true);
            }
            if (file.equals("pom.xml")) {
    result.setMavenProject(true);
}

if (file.equals("build.gradle")) {
    result.setGradleProject(true);
}
if (file.equals("application.yml")
        || file.equals("application.yaml")
        || file.equals("application.properties")) {

    result.setSpringBootProject(true);
}
        }

        return result;
    }
}