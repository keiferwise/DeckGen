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
import com.kif.deckgenmodels.*;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CardService {
    private static final Logger logger = LoggerFactory.getLogger(CardService.class);

	@Value("${com.kif.sharedsecret}")
	String key;
    private final WebClient webClient;

    public CardService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:1010").build();
    }
	

    public Mono<String> createSingle(String name, String type, String subtype, String theme,String artStyle,String vibe, String mana,String deckId){
    	
    	
    	String requestBody;
    	
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
    	
    	requestBody = convertSingleToJson(sr);
    	
		return webClient.post()
	            .uri("/create-card")
	            .header(HttpHeaders.CONTENT_TYPE, "application/json") // Set the Content-Type header
	            .bodyValue(requestBody)
	            .retrieve()
	            .bodyToMono(String.class);
    }
    
    
	public Mono<String> createCard(String cardId, String theme, String deckId) {
		System.out.println("sending request to card microservice");
		
		System.out.println("Trying to make this into a request JSON: "+cardId + " "+theme+ " "+deckId);
		ObjectMapper mapper = new ObjectMapper();
		
		CardRequest cr = new CardRequest(cardId, theme, deckId,calculateSHA256Hash(key));
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
	
	private String convertSingleToJson(SingleRequest sr) {
		String json="";
		ObjectMapper mapper = new ObjectMapper();
		try {
			 json=mapper.writeValueAsString(sr);
			 System.out.println("Object converted to JSON String: " + json.toString());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public static String calculateSHA256Hash(String input) {
    	Date date = new Date();
    	String seededKey = input + date.toString().substring(0, 16);
    	
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
