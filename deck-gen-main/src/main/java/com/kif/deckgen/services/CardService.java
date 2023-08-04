package com.kif.deckgen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;


@Service
public class CardService {

    private final WebClient webClient;

    public CardService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:1010").build();
    }
	

	public Mono<String> createCard() {
		System.out.println("sending request to card microservice");
		String requestBody = "{\"thing\":\"thing\";)";
		 return webClient.post()
		            .uri("/create-card-for-deck")
		            .bodyValue(requestBody)
		            .retrieve()
		            .bodyToMono(String.class);
		
	}

}
