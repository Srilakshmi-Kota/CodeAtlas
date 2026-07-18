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
                You are a senior software engineer.

                Analyze this project:

                Java Project: %s
                Spring Boot: %s
                Maven: %s
                Gradle: %s
                Docker: %s
                Has Tests: %s

                Explain this project in simple English in 5-6 lines.
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