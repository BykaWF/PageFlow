package com.project.pageflow.controller;

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.huggingface.HuggingfaceChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("api/v1")
public class AIController {

    private final String API_URL = "https://api-inference.huggingface.co/models/mistralai/Mistral-7B-Instruct-v0.2";

    @Value("${TOKEN}")
    private String TOKEN;

    @PostMapping("/speak")
    public String speakWithModel(@RequestBody String input) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(input, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(API_URL, requestEntity, String.class);

        // Parse the response
        JSONArray responseArray = new JSONArray(responseEntity.getBody());
        JSONObject responseObject = responseArray.getJSONObject(0);
        String aiReply = responseObject.getString("generated_text");


        return aiReply;
    }



}