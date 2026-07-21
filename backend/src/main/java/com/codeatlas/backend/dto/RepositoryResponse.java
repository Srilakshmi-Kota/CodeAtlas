package com.codeatlas.backend.dto;

public class RepositoryResponse {

    private String owner;
    private String repository;
    private boolean valid;
    private String aiSummary;
    private String language;
private int stars;
private int healthScore;
private boolean javaProject;
private boolean mavenProject;
private boolean gradleProject;
private boolean dockerProject;
private boolean hasTests;
private boolean hasReadme;
private boolean hasCiCd;
private boolean hasDocumentation;
private boolean hasLicense;
private boolean springBootProject;
private String ownerAvatar;
private int forks;
private int watchers;
private String aiRecommendations;
private int javaFileCount;
private int controllerCount;
private int serviceCount;
private int repositoryCount;
private int entityCount;
private int configurationCount;
private int testFileCount;
public int getForks() {
    return forks;
}

public void setForks(int forks) {
    this.forks = forks;
}

public int getWatchers() {
    return watchers;
}

public void setWatchers(int watchers) {
    this.watchers = watchers;
}

public int getOpenIssues() {
    return openIssues;
}

public void setOpenIssues(int openIssues) {
    this.openIssues = openIssues;
}

public String getLastUpdated() {
    return lastUpdated;
}

public void setLastUpdated(String lastUpdated) {
    this.lastUpdated = lastUpdated;
}

private int openIssues;
private String lastUpdated;
    public RepositoryResponse() {
    }

   public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public void setOwnerAvatar(String ownerAvatar) {
        this.ownerAvatar = ownerAvatar;
    }

   public RepositoryResponse(
        String owner,
        String repository,
        boolean valid,
        String aiSummary,
        int healthScore) {

    this.owner = owner;
    this.repository = repository;
    this.valid = valid;
    this.aiSummary = aiSummary;
    this.healthScore = healthScore;
}
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    public String getAiSummary() {
    return aiSummary;
}
public void setAiSummary(String aiSummary) {
    this.aiSummary = aiSummary;
}

public String getLanguage() {
    return language;
}

public void setLanguage(String language) {
    this.language = language;
}

public int getStars() {
    return stars;
}

public void setStars(int stars) {
    this.stars = stars;
}

public boolean isJavaProject() {
    return javaProject;
}

public void setJavaProject(boolean javaProject) {
    this.javaProject = javaProject;
}

public boolean isMavenProject() {
    return mavenProject;
}

public void setMavenProject(boolean mavenProject) {
    this.mavenProject = mavenProject;
}

public boolean isGradleProject() {
    return gradleProject;
}

public void setGradleProject(boolean gradleProject) {
    this.gradleProject = gradleProject;
}

public boolean isDockerProject() {
    return dockerProject;
}

public void setDockerProject(boolean dockerProject) {
    this.dockerProject = dockerProject;
}

public boolean isHasTests() {
    return hasTests;
}

public void setHasTests(boolean hasTests) {
    this.hasTests = hasTests;
}
public int getHealthScore() {
    return healthScore;
}

public void setHealthScore(int healthScore) {
    this.healthScore = healthScore;
}
public boolean isSpringBootProject() {
    return springBootProject;
}

public void setSpringBootProject(boolean springBootProject) {
    this.springBootProject = springBootProject;
}
public String getAiRecommendations() {
    return aiRecommendations;
}

public void setAiRecommendations(String aiRecommendations) {
    this.aiRecommendations = aiRecommendations;
}
public boolean isHasReadme() {
    return hasReadme;
}

public void setHasReadme(boolean hasReadme) {
    this.hasReadme = hasReadme;
}

public boolean isHasCiCd() {
    return hasCiCd;
}

public void setHasCiCd(boolean hasCiCd) {
    this.hasCiCd = hasCiCd;
}

public boolean isHasDocumentation() {
    return hasDocumentation;
}

public void setHasDocumentation(boolean hasDocumentation) {
    this.hasDocumentation = hasDocumentation;
}

public boolean isHasLicense() {
    return hasLicense;
}

public void setHasLicense(boolean hasLicense) {
    this.hasLicense = hasLicense;
}
public int getJavaFileCount() {
    return javaFileCount;
}

public void setJavaFileCount(int javaFileCount) {
    this.javaFileCount = javaFileCount;
}

public int getControllerCount() {
    return controllerCount;
}

public void setControllerCount(int controllerCount) {
    this.controllerCount = controllerCount;
}

public int getServiceCount() {
    return serviceCount;
}

public void setServiceCount(int serviceCount) {
    this.serviceCount = serviceCount;
}

public int getRepositoryCount() {
    return repositoryCount;
}

public void setRepositoryCount(int repositoryCount) {
    this.repositoryCount = repositoryCount;
}

public int getEntityCount() {
    return entityCount;
}

public void setEntityCount(int entityCount) {
    this.entityCount = entityCount;
}

public int getConfigurationCount() {
    return configurationCount;
}

public void setConfigurationCount(int configurationCount) {
    this.configurationCount = configurationCount;
}

public int getTestFileCount() {
    return testFileCount;
}

public void setTestFileCount(int testFileCount) {
    this.testFileCount = testFileCount;
}
}
