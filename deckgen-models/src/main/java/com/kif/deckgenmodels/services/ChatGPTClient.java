package com.kif.deckgenmodels.services;
import com.kif.deckgenmodels.ChatRequest;
import com.kif.deckgenmodels.Message;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.annotation.PropertySource;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kif.deckgenmodels.Card;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PropertySource("classpath:application.properties")
@Service("ChatGPTClient")
public class ChatGPTClient {
	@Value("${com.kif.api-key}")
    private String API_KEY;
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    
    
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public ChatGPTClient() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        //System.out.println(API_KEY);
        //System.out.println("hello");
    }

    public String generateCompletion(String prompt, int maxTokens) {
	    String model = "gpt-3.5-turbo-1106";

    	ChatRequest request = new ChatRequest(model, prompt);
    	
    	
    	HttpEntity<String> requestEntity2 = createRequestEntity2(prompt,"user",model);
    	
    	//HttpEntity<String> requestEntity = createRequestEntity(prompt, maxTokens);
        
        
        ResponseEntity<String> responseEntity = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity2, String.class);

        
        
        
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            try {
                JsonNode rootNode = objectMapper.readTree(responseEntity.getBody());
                System.out.println(rootNode.toPrettyString());

                String completion = rootNode.path("choices").get(0).path("message").path("content").asText();
                System.out.println("my completetion: "+completion);
                return completion.trim();
            } catch (Exception e) {
                System.err.println("Failed to parse JSON response: " + e.getMessage());
                return "FAILED";
            }
        } else {
            System.err.println("Request failed. Response code: " + responseEntity.getStatusCode());
            return "FAILED";
        }
    }
/*
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
    */

    private HttpEntity<String> createRequestEntity2(String prompt,String role,String model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + API_KEY);
        //System.out.println(API_KEY);

        //ChatRequest request = new ChatRequest(model, prompt);
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("user", prompt));
        Map<String, String> responseFormat = new HashMap<>();
        responseFormat.put("type", "json_object");        
        try {
            Map<String, Object> requestBody = new HashMap<>();
            //requestBody.put("prompt", prompt);
            //requestBody.put("max_tokens", maxTokens);
            //requestBody.put("temperature", 0.5); // Adjust this value to change randomness
            //requestBody.put("top_p", 1); // Adjust this value to change diversity
            //requestBody.put("n", 1);
            //requestBody.put("model", "text-davinci-003"); // Number of completions to generate
            requestBody.put("model", model);
            requestBody.put("messages", messages);
            requestBody.put("response_format", responseFormat);
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

