package com.kif.deckservice.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.kif.deckgenmodels.*;
//import com.kif.deckservice.util.ApiKeyUtil;
//import com.kif.deckservice.config.AppProperties;
import com.kif.deckservice.config.AppProperties;

import reactor.core.publisher.Mono;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CardService {
	
	@Autowired
	private AppProperties props;
	@Value("${com.kif.sharedsecret}")
	private String secret;
	
    private final WebClient webClient;

    public CardService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:1010").build();
    }
	

	public Mono<String> createCard(String cardId, String theme, String deckId) {
		//System.out.println("sending request to card microservice");
		System.out.println("my key: "+secret);
		System.out.println("from props: "+props.getSharedsecret());

		//System.out.println("Trying to make this into a request JSON: "+cardId + " "+theme+ " "+deckId);
		ObjectMapper mapper = new ObjectMapper();
		
		CardRequest cr = new CardRequest(cardId, theme, deckId, calculateSHA256Hash(secret));
		String requestBody="{\"cardId\":\""+cardId+"\",\"theme\":"+theme+",\"deckIdeaId\":\""+deckId+"}";
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

	public String calculateSHA256Hash(String input) {
    	Date date = new Date();
    	String seededKey = input + date.toString().substring(0, 16);
    	System.out.println("this is my key seed" + seededKey);
        try {
            // Create a MessageDigest object with the SHA-256 algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Convert the input string to bytes
            byte[] encodedHash = digest.digest(seededKey.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Return the SHA-256 hash as a string
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
