package com.codeatlas.backend.scanner;

import java.util.ArrayList;
import java.util.List;

public class ScannerResult {

    private boolean javaProject;
    private boolean springBoot;
    private boolean mavenProject;
    private boolean gradleProject;
    private boolean dockerProject;
    private boolean hasTests;
    private boolean springBootProject;
    private boolean hasReadme;
    private boolean hasCiCd;
    private boolean hasDocumentation;
    private boolean hasLicense;
    private int javaFileCount;
    private int controllerCount;
    private int serviceCount;
    private int repositoryCount;
    private int entityCount;
    private int configurationCount;
    private int testFileCount;
    private String javaVersion;
private String springBootVersion;
private String database;
private String securityFramework;
private String testingFramework;
private String documentationFramework;
private String ormFramework;
private String buildTool;
private int dependencyCount;

private boolean hasWebDependency;
private boolean hasDataDependency;
private boolean hasDatabaseDriver;
private boolean hasTestingDependency;
private boolean hasSecurityDependency;
private int getEndpointCount;
private int postEndpointCount;
private int putEndpointCount;
private int deleteEndpointCount;
private int patchEndpointCount;
private List<String> dependencies = new ArrayList<>();
private List<String> buildPlugins = new ArrayList<>();
    public ScannerResult() {
    }

    public boolean isJavaProject() {
        return javaProject;
    }

    public void setJavaProject(boolean javaProject) {
        this.javaProject = javaProject;
    }

    public boolean isSpringBoot() {
        return springBoot;
    }

    public void setSpringBoot(boolean springBoot) {
        this.springBoot = springBoot;
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

    public boolean isSpringBootProject() {
        return springBootProject;
    }

    public void setSpringBootProject(boolean springBootProject) {
        this.springBootProject = springBootProject;
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
public String getJavaVersion() {
    return javaVersion;
}

public void setJavaVersion(String javaVersion) {
    this.javaVersion = javaVersion;
}

public String getSpringBootVersion() {
    return springBootVersion;
}

public void setSpringBootVersion(String springBootVersion) {
    this.springBootVersion = springBootVersion;
}

public String getDatabase() {
    return database;
}

public void setDatabase(String database) {
    this.database = database;
}

public String getSecurityFramework() {
    return securityFramework;
}

public void setSecurityFramework(String securityFramework) {
    this.securityFramework = securityFramework;
}

public String getTestingFramework() {
    return testingFramework;
}

public void setTestingFramework(String testingFramework) {
    this.testingFramework = testingFramework;
}

public String getDocumentationFramework() {
    return documentationFramework;
}

public void setDocumentationFramework(String documentationFramework) {
    this.documentationFramework = documentationFramework;
}

public String getOrmFramework() {
    return ormFramework;
}

public void setOrmFramework(String ormFramework) {
    this.ormFramework = ormFramework;
}

public String getBuildTool() {
    return buildTool;
}

public void setBuildTool(String buildTool) {
    this.buildTool = buildTool;
}

public List<String> getBuildPlugins() {
    return buildPlugins;
}

public void setBuildPlugins(List<String> buildPlugins) {
    this.buildPlugins = buildPlugins;
}
public int getDependencyCount() {
    return dependencyCount;
}

public void setDependencyCount(int dependencyCount) {
    this.dependencyCount = dependencyCount;
}

public boolean isHasWebDependency() {
    return hasWebDependency;
}

public void setHasWebDependency(boolean hasWebDependency) {
    this.hasWebDependency = hasWebDependency;
}

public boolean isHasDataDependency() {
    return hasDataDependency;
}

public void setHasDataDependency(boolean hasDataDependency) {
    this.hasDataDependency = hasDataDependency;
}

public boolean isHasDatabaseDriver() {
    return hasDatabaseDriver;
}

public void setHasDatabaseDriver(boolean hasDatabaseDriver) {
    this.hasDatabaseDriver = hasDatabaseDriver;
}

public boolean isHasTestingDependency() {
    return hasTestingDependency;
}

public void setHasTestingDependency(boolean hasTestingDependency) {
    this.hasTestingDependency = hasTestingDependency;
}

public boolean isHasSecurityDependency() {
    return hasSecurityDependency;
}

public void setHasSecurityDependency(boolean hasSecurityDependency) {
    this.hasSecurityDependency = hasSecurityDependency;
}

public List<String> getDependencies() {
    return dependencies;
}

public void setDependencies(List<String> dependencies) {
    this.dependencies = dependencies;
}
public int getGetEndpointCount() {
    return getEndpointCount;
}

public void setGetEndpointCount(int getEndpointCount) {
    this.getEndpointCount = getEndpointCount;
}

public int getPostEndpointCount() {
    return postEndpointCount;
}

public void setPostEndpointCount(int postEndpointCount) {
    this.postEndpointCount = postEndpointCount;
}

public int getPutEndpointCount() {
    return putEndpointCount;
}

public void setPutEndpointCount(int putEndpointCount) {
    this.putEndpointCount = putEndpointCount;
}

public int getDeleteEndpointCount() {
    return deleteEndpointCount;
}

public void setDeleteEndpointCount(int deleteEndpointCount) {
    this.deleteEndpointCount = deleteEndpointCount;
}

public int getPatchEndpointCount() {
    return patchEndpointCount;
}

public void setPatchEndpointCount(int patchEndpointCount) {
    this.patchEndpointCount = patchEndpointCount;
}

public int getTotalEndpointCount() {
    return getEndpointCount
            + postEndpointCount
            + putEndpointCount
            + deleteEndpointCount
            + patchEndpointCount;
}
}