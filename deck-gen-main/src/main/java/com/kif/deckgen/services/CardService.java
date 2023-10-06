package com.kif.deckgen.services;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.kif.deckgenmodels.*;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CardService {

    private final WebClient webClient;

    public CardService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:1010").build();
    }
	

    public Mono<String> createSingle(String name, String type, String theme,String artStyle,String vibe, String mana){
    	
    	
    	String requestBody= "1";
    	
    	
    	
		return webClient.post()
	            .uri("/create-card-for-deck")
	            .header(HttpHeaders.CONTENT_TYPE, "application/json") // Set the Content-Type header
	            .bodyValue(requestBody)
	            .retrieve()
	            .bodyToMono(String.class);
    }
    
    
	public Mono<String> createCard(String cardId, String theme, String deckId) {
		System.out.println("sending request to card microservice");
		
		System.out.println("Trying to make this into a request JSON: "+cardId + " "+theme+ " "+deckId);
		ObjectMapper mapper = new ObjectMapper();
		
		CardRequest cr = new CardRequest(cardId, theme, deckId);
		String requestBody=null;//"{\"cardId\":\""+cardId+"\",\"theme\":"+theme+",\"deckIdeaId\":\""+deckId+"}";
		try {
			 requestBody=mapper.writeValueAsString(cr);
			 System.out.println("Object converted to JSON String: " + requestBody.toString());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(cr.toString());
		//String requestBody="{\"cardId\":\""+cardId+"\",\"theme\":"+theme+",\"deckIdeaId\":\""+deckId+"}";

		System.out.println(requestBody);
		return webClient.post()
		            .uri("/create-card-for-deck")
		            .header(HttpHeaders.CONTENT_TYPE, "application/json") // Set the Content-Type header
		            .bodyValue(requestBody)
		            .retrieve()
		            .bodyToMono(String.class);
		
	}

}
