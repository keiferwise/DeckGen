package com.kif.deckservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kif.deckgenmodels.DeckRequest;
import com.kif.deckservice.util.ApiKeyUtil;
import com.kif.deckservice.services.DeckGenerator;

@RestController
public class DeckController {
	@Autowired
	DeckGenerator deckGenerator;
	@Value("${com.kif.sharedsecret}")
	String key;
	@Autowired
	ApiKeyUtil keyUtil;
	
	public DeckController() {
		// TODO Auto-generated constructor stub
	}
	@PostMapping(value = "/create-deck")
	public ResponseEntity<String> createDeck(@RequestBody DeckRequest dr) {
		
		
		if(dr.getKey().equals(keyUtil.calculateSHA256Hash(key))) {

			System.out.println("creating Deck");
			System.out.println("deck Idea: "+dr.getDeckIdeaId().toString());
			System.out.println("deck: "+dr.getDeckId().toString());
	
			deckGenerator.makeDeck(dr.getDeckId(),dr.getDeckIdeaId());
			// Create something to 
			
			// CreateCard(Card card, String theme, DeckIdea deckIdea)
			return ResponseEntity.ok("Request received successfully!");
		}
		else {
			return ResponseEntity.badRequest().body("Bad Key");

		}
	}
}
