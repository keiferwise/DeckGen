package com.kif.deckgen.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kif.deckgenmodels.CardRequest;
import com.kif.deckgenmodels.SingleRequest;
import reactor.core.publisher.Mono;

@Service
public class CardService {
    private static final Logger logger = LoggerFactory.getLogger(CardService.class);

    @Value("${com.kif.sharedsecret}")
    private String key;

    private final WebClient webClient;

    public CardService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:1010").build();
    }

    public Mono<String> createSingle(String name, String type, String subtype, String theme, String artStyle, String vibe, String mana, String deckId) {
        logger.info("Starting createSingle method for name: {}, type: {}, theme: {}, deckId: {}", name, type, theme, deckId);

        SingleRequest sr = new SingleRequest();
        sr.setArtStyle(artStyle);
        sr.setMana(mana);
        sr.setName(name);
        sr.setTheme(theme);
        sr.setType(type);
        sr.setSubType(subtype);
        sr.setVibe(vibe);
        sr.setDeckId(deckId);

        String newKey = calculateSHA256Hash(key);
        sr.setKey(newKey);

        String requestBody = convertSingleToJson(sr);
        if (requestBody == null) {
            logger.error("Failed to convert SingleRequest to JSON for name: {}", name);
            return Mono.error(new RuntimeException("JSON conversion failed"));
        }

        logger.debug("Generated request body: {}", requestBody);

        return webClient.post()
                .uri("/create-card")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> logger.info("Successfully created card for name: {}", name))
                .doOnError(error -> logger.error("Error creating card for name: {}", name, error));
    }

    public Mono<String> createCard(String cardId, String theme, String deckId) {
        logger.info("Starting createCard method for cardId: {}, theme: {}, deckId: {}", cardId, theme, deckId);

        CardRequest cr = new CardRequest(cardId, theme, deckId, calculateSHA256Hash(key));

        String requestBody;
        try {
            requestBody = new ObjectMapper().writeValueAsString(cr);
            logger.debug("Generated request body: {}", requestBody);
        } catch (JsonProcessingException e) {
            logger.error("Error converting CardRequest to JSON for cardId: {}", cardId, e);
            return Mono.error(e);
        }

        return webClient.post()
                .uri("/create-card-for-deck")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> logger.info("Successfully created card for cardId: {}", cardId))
                .doOnError(error -> logger.error("Error creating card for cardId: {}", cardId, error));
    }

    private String convertSingleToJson(SingleRequest sr) {
        logger.debug("Converting SingleRequest to JSON: {}", sr);
        try {
            return new ObjectMapper().writeValueAsString(sr);
        } catch (JsonProcessingException e) {
            logger.error("Error converting SingleRequest to JSON: {}", sr, e);
            return null;
        }
    }

    public static String calculateSHA256Hash(String input) {
        Date date = new Date();
        String seededKey = input + date.toString().substring(0, 16);
        logger.debug("Calculating SHA-256 hash for seededKey: {}", seededKey);

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(seededKey.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            logger.debug("Generated SHA-256 hash: {}", hexString.toString());
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("SHA-256 algorithm not found", e);
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
