package com.project.pageflow.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class AIController {

    private final RestTemplate restTemplate;
    private final String baseApiUrl;
    private final String apiKey;

    public AIController(RestTemplate restTemplate,
                        @Value("${spring.ai.ollama.base-url}") String baseApiUrl,
                        @Value("${api.token}") String apiKey) {
        this.restTemplate = restTemplate;
        this.baseApiUrl = baseApiUrl;
        this.apiKey = apiKey;
    }

    @PostMapping("/ai/generate")
    public String generate(@RequestBody Map<String, String> input) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = Collections.singletonMap("messages",
                Collections.singletonList(Map.of("role", "user", "content", input.get("message"))));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                baseApiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String responseBody = responseEntity.getBody();

        // Debug statement
        System.out.println("Response Body: " + responseBody);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(responseBody);
            return root.path("result").path("response").asText();
        } catch (Exception e) {
            throw new RuntimeException("Error parsing response body", e);
        }
    }

}