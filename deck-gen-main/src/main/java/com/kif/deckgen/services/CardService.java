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
		 return webClient.get()
		            .uri("/create-card-for-deck")
		            .retrieve()
		            .bodyToMono(String.class);
		
	}

}
