package com.codeatlas.backend.scanner;

public class ApiEndpoint {

    private String method;
    private String path;

    public ApiEndpoint() {
    }

    public ApiEndpoint(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}