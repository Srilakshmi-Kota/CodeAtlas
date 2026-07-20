package com.codeatlas.backend.scanner;

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
}