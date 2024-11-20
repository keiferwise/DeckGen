package com.kif.deckgen.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kif.deckgenmodels.DeckRequest;
import com.kif.deckgenmodels.util.ApiKeyUtil;

import reactor.core.publisher.Mono;

@Service
public class DeckService {
    private static final Logger logger = LoggerFactory.getLogger(DeckService.class);

    @Autowired
    private ApiKeyUtil keyUtil;

    @Value("${com.kif.sharedsecret}")
    private String key;

    private final WebClient webClient;

    public DeckService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:1011").build();
    }

    public Mono<String> createDeck(String deckIdeaId, String deckId) {
        logger.info("Starting createDeck for deckIdeaId: {} and deckId: {}", deckIdeaId, deckId);

        // Create DeckRequest object
        DeckRequest dr = new DeckRequest(deckIdeaId, deckId, keyUtil.calculateSHA256Hash(key));

        // Serialize DeckRequest to JSON
        String requestBody;
        try {
            requestBody = new ObjectMapper().writeValueAsString(dr);
            logger.debug("Generated JSON request body: {}", requestBody);
        } catch (JsonProcessingException e) {
            logger.error("Error serializing DeckRequest to JSON for deckIdeaId: {}", deckIdeaId, e);
            return Mono.error(e);
        }

        // Log DeckRequest for debugging
        logger.debug("DeckRequest object: {}", dr);

        // Send POST request to create deck
        return webClient.post()
                .uri("/create-deck")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> logger.info("Successfully created deck for deckIdeaId: {} and deckId: {}", deckIdeaId, deckId))
                .doOnError(error -> logger.error("Error creating deck for deckIdeaId: {} and deckId: {}", deckIdeaId, deckId, error));
    }
}
