package com.kif.deckgen.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.annotation.PropertySource;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kif.deckgen.models.Card;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@PropertySource("classpath:application.properties")
@Service("ChatGPTClient")
public class ChatGPTClient {
	@Value("${com.kif.api-key}")
    private String API_KEY;
    private static final String API_URL = "https://api.openai.com/v1/completions";
    
    
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public ChatGPTClient() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        //System.out.println(API_KEY);
        //System.out.println("hello");
    }

    public String generateCompletion(String prompt, int maxTokens) {
        HttpEntity<String> requestEntity = createRequestEntity(prompt, maxTokens);
        ResponseEntity<String> responseEntity = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            try {
                JsonNode rootNode = objectMapper.readTree(responseEntity.getBody());
                String completion = rootNode.path("choices").get(0).path("text").asText();
                return completion.trim();
            } catch (Exception e) {
                System.err.println("Failed to parse JSON response: " + e.getMessage());
                return null;
            }
        } else {
            System.err.println("Request failed. Response code: " + responseEntity.getStatusCode());
            return null;
        }
    }

    private HttpEntity<String> createRequestEntity(String prompt, int maxTokens) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + API_KEY);

        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("prompt", prompt);
            requestBody.put("max_tokens", maxTokens);
            requestBody.put("temperature", 0.5); // Adjust this value to change randomness
            requestBody.put("top_p", 1); // Adjust this value to change diversity
            requestBody.put("n", 1);
            requestBody.put("model", "text-davinci-003"); // Number of completions to generate

            String jsonBody = objectMapper.writeValueAsString(requestBody);
            return new HttpEntity<>(jsonBody, headers);
        } catch (Exception e) {
            System.err.println("Failed to create JSON request body: " + e.getMessage());
            return null;
        }
    }
    //TODO
    public String generateDeck(String Prompt) {
    	return null;
    }
    //TODO
    public String generateCard(String Prompt) {
    	return null;
    }
    //TODO
    public String regenerateCard(Card card) {
		return null;
    }
    
    
    
    /*public static void main(String[] args) {
        ChatGPTClient chatGPTClient = new ChatGPTClient();
        String prompt = "Create a list of magic the gathering cards based off the following theme: Stone Ball Crushing. Create 8 Creatures, 3 Instants, 3 Enchantments, 3 Sorcery and 4 Artifacts. All must not be existing card names. return in json format";
        int maxTokens = 500;
        String completion = chatGPTClient.generateCompletion(prompt, maxTokens);

        if (completion != null) {
            System.out.println("Generated text: " + completion);
        } else {
            System.err.println("No completion generated.");
        }
    }*/
}

