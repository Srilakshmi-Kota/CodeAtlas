package com.codeatlas.backend.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.codeatlas.backend.scanner.ScannerResult;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

@Service
public class GeminiService implements AIService {

    @Value("${gemini.api-key}")
    private String apiKey;

    @Override
    public String generateSummary(ScannerResult scannerResult) {

        Client client = Client.builder()
                .apiKey(apiKey)
                .build();

        String prompt = """
        You are a senior software engineer analyzing a GitHub repository.

        The following facts were detected directly from the repository.
        Treat these values as authoritative. Do not contradict them.

        Java Project: %s
        Spring Boot Project: %s
        Maven Project: %s
        Gradle Project: %s
        Docker: %s
        Automated Tests: %s
        README: %s
        CI/CD: %s
        Documentation: %s
        License: %s

        Write a concise 4-5 sentence technical summary.

        Rules:
        - Never claim that a detected technology is absent when its value is true.
        - Do not assume missing technologies beyond the facts provided.
        - Mention the strongest engineering practices detected.
        - Mention important missing practices only when their value is false.
        - Do not invent repository features.
        """.formatted(
        scannerResult.isJavaProject(),
        scannerResult.isSpringBootProject(),
        scannerResult.isMavenProject(),
        scannerResult.isGradleProject(),
        scannerResult.isDockerProject(),
        scannerResult.isHasTests(),
        scannerResult.isHasReadme(),
        scannerResult.isHasCiCd(),
        scannerResult.isHasDocumentation(),
        scannerResult.isHasLicense()
);

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        null);

        return response.text();
    }
    public String generateRecommendations(ScannerResult scannerResult) {

    Client client = Client.builder()
            .apiKey(apiKey)
            .build();

    String prompt = """
            You are a senior software engineer performing a GitHub repository review.

            Repository analysis:

            Java Project: %s
            Spring Boot: %s
            Maven: %s
            Gradle: %s
            Docker: %s
            Has Tests: %s

            Give exactly 5 short, practical recommendations to improve
            this repository.

            Focus on:
            - testing
            - code quality
            - CI/CD
            - documentation
            - security or deployment

            Do not use markdown headings.
            Keep each recommendation concise and put each recommendation
            on a separate line.
            """.formatted(
            scannerResult.isJavaProject(),
            scannerResult.isSpringBoot(),
            scannerResult.isMavenProject(),
            scannerResult.isGradleProject(),
            scannerResult.isDockerProject(),
            scannerResult.isHasTests());

    GenerateContentResponse response =
            client.models.generateContent(
                    "gemini-2.5-flash",
                    prompt,
                    null);

    return response.text();
}
}