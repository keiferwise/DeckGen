package com.kif.deckgen.services;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.kif.deckgenmodels.*;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DeckService {

	
    private final WebClient webClient;

    public DeckService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:1011").build();
    }

	public Mono<String> createDeck(String deckIdeaId, String deckId) {
		System.out.println("sending request to deck microservice");
		
		System.out.println("Trying to make this into a request JSON: "+deckIdeaId + " "+deckId);
		ObjectMapper mapper = new ObjectMapper();
		
		DeckRequest dr = new DeckRequest(deckIdeaId, deckId);
		String requestBody=null;//"{\"cardId\":\""+cardId+"\",\"theme\":"+theme+",\"deckIdeaId\":\""+deckId+"}";
		try {
			 requestBody=mapper.writeValueAsString(dr);
			 System.out.println("Object converted to JSON String: " + requestBody.toString());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(dr.toString());

		System.out.println(requestBody);
		return webClient.post()
		            .uri("/create-card-for-deck")
		            .header(HttpHeaders.CONTENT_TYPE, "application/json") // Set the Content-Type header
		            .bodyValue(requestBody)
		            .retrieve()
		            .bodyToMono(String.class);
		
	}

}
