package com.kif.deckservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.kif.deckgenmodels.*;
import com.kif.deckgenmodels.util.ApiKeyUtil;

import reactor.core.publisher.Mono;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CardService {
    private static final Logger logger = LoggerFactory.getLogger(CardService.class);

	
	//@Value("${com.kif.sharedsecret}")
	//String key;
	
	
	ApiKeyUtil keyUtil;
	
    private final WebClient webClient;

    public CardService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:1010").build();
    }
	

	public Mono<String> createCard(String cardId, String theme, String deckId, String key) {
		keyUtil = new ApiKeyUtil();
		ObjectMapper mapper = new ObjectMapper();
		
		CardRequest cr = new CardRequest(cardId, theme, deckId,keyUtil.calculateSHA256Hash(key));
		String requestBody="{\"cardId\":\""+cardId+"\",\"theme\":"+theme+",\"deckIdeaId\":\""+deckId+"}";
		try {
			 requestBody=mapper.writeValueAsString(cr);
			 logger.info("Object converted to JSON String: " + requestBody.toString());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Error occured when trying to convert JSON to string"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		//System.out.println("card request about to send of id "+cr.getCardId());
		//String requestBody="{\"cardId\":\""+cardId+"\",\"theme\":"+theme+",\"deckIdeaId\":\""+deckId+"}";

		//System.out.println(requestBody);
		return webClient.post()
		            .uri("/create-card-for-deck")
		            .header(HttpHeaders.CONTENT_TYPE, "application/json") // Set the Content-Type header
		            .bodyValue(requestBody)
		            .retrieve()
		            .bodyToMono(String.class);
		
	}

}
