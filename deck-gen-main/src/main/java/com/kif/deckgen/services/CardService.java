package com.kif.deckgen.services;

import org.springframework.beans.factory.annotation.Autowired;
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
	

	public Mono<String> createCard(String cardId, String theme, String deckId) {
		System.out.println("sending request to card microservice");
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		CardRequest cr = new CardRequest(cardId, theme, deckId);
		
		System.out.println(cr.toString());
		String requestBody=null;
		try {
			requestBody = mapper.writeValueAsString(cr);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(requestBody);
		 return webClient.post()
		            .uri("/create-card-for-deck")
		            .bodyValue(requestBody)
		            .retrieve()
		            .bodyToMono(String.class);
		
	}

}
