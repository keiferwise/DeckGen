package com.kif.deckgenmodels.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kif.deckgenmodels.ChatRequest;
import com.kif.deckgenmodels.ChatResponse;

@Service
public class ChatApiClient {

	public ChatApiClient() {
		// TODO Auto-generated constructor stub
	}

	    
	    @Qualifier("openaiRestTemplate")
	    @Autowired
	    private RestTemplate restTemplate;
	    
	    private String model = "gpt-3.5-turbo-1106";
	    
	    private String apiUrl = "https://api.openai.com/v1/chat/completions";
	    
	    public String chat(String prompt) {
	        // create a request
	        ChatRequest request = new ChatRequest(model, prompt);
	        System.out.println("message: " + request.getMessages().toString());

	        // call the API
	        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);
	        
	        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
	            return "No response";
	        }
	        
	        // return the first response
	        return response.getChoices().get(0).getMessage().getContent();
	    
	}

}
