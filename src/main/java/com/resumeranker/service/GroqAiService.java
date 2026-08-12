package com.resumeranker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GroqAiService {

    @Value("${groq.api.key:}")
    private String defaultGroqApiKey;

    @Value("${groq.model:llama-3.3-70b-versatile}")
    private String groqModel;

    @Value("${groq.api.url:https://api.groq.com/openai/v1/chat/completions}")
    private String groqApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateAiAdvice(String jobTitle, String jobDesc, String resumeText, List<String> missingSkills, String customApiKey) {
        String apiKey = (customApiKey != null && !customApiKey.trim().isEmpty()) ? customApiKey.trim() : defaultGroqApiKey;

        if (apiKey == null || apiKey.trim().isEmpty()) {
            return null; // Return null if no Groq key configured
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            String systemPrompt = "You are an expert ATS Resume Coach and Technical Hiring Manager. Analyze the candidate resume against the job description and missing skills. Provide concise, bulleted, actionable advice to help the candidate optimize their resume for ATS compliance and land the interview.";
            String userPrompt = String.format(
                "Job Title: %s\nMissing Required Skills: %s\n\nJob Description:\n%s\n\nCandidate Resume Summary:\n%s",
                jobTitle, String.join(", ", missingSkills),
                jobDesc.length() > 1000 ? jobDesc.substring(0, 1000) : jobDesc,
                resumeText.length() > 1500 ? resumeText.substring(0, 1500) : resumeText
            );

            Map<String, Object> messageSystem = Map.of("role", "system", "content", systemPrompt);
            Map<String, Object> messageUser = Map.of("role", "user", "content", userPrompt);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", groqModel);
            requestBody.put("messages", List.of(messageSystem, messageUser));
            requestBody.put("temperature", 0.6);
            requestBody.put("max_tokens", 350);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(groqApiUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode contentNode = root.path("choices").get(0).path("message").path("content");
                if (!contentNode.isMissingNode()) {
                    return contentNode.asText();
                }
            }
        } catch (Exception e) {
            System.err.println("Groq AI API Call skipped or failed: " + e.getMessage());
        }
        return null;
    }
}
