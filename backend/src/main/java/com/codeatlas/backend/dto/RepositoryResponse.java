package com.codeatlas.backend.dto;

public class RepositoryResponse {

    private String owner;
    private String repository;
    private boolean valid;

    public RepositoryResponse() {
    }

    public RepositoryResponse(String owner, String repository, boolean valid) {
        this.owner = owner;
        this.repository = repository;
        this.valid = valid;
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
}