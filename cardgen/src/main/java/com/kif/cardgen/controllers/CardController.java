package com.kif.cardgen.controllers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kif.deckgenmodels.daos.CardDao;
import com.kif.cardgen.services.CardGenerator;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.CardRequest;
import com.kif.deckgenmodels.SingleRequest;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CardController {
	@Autowired
	CardDao cardDao;
	@Autowired
	CardGenerator cardGenerator;
	@Value("${com.kif.sharedsecret}")
	String key;
	
	public CardController() {
		// TODO Auto-generated constructor stub
	}
	
	@PostMapping(value = "/create-card-for-deck", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createCardForDeck(@RequestBody CardRequest cr) {
		
		
		cardGenerator.createCard(cr.getCardId(), cr.getTheme(), cr.getDeckIdeaId());
		return ResponseEntity.ok("Request received successfully!");
	}
	
	
	@PostMapping("/create-card")
	public ResponseEntity<String> createCard(@RequestBody SingleRequest sr) {
		Card nc = new Card();
		String newCardId = UUID.randomUUID().toString();
		if(sr.getKey().equals(calculateSHA256Hash(key))) {
			nc = cardGenerator.createSingleCard(sr,newCardId);
			cardDao.save(nc,sr.getDeckId(),newCardId); 
			return ResponseEntity.ok(newCardId);

		}
		else {
			return ResponseEntity.badRequest().body("Bad Key");

		}


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
