package com.kif.deckservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kif.deckgenmodels.DeckRequest;
import com.kif.deckgenmodels.services.DalleClient;
import com.kif.deckservice.services.DeckGenerator;

@RestController
public class DeckController {
	@Autowired
	DeckGenerator deckGenerator;
	@Value("${com.kif.sharedsecret}")
	String key;
    private static final Logger logger = LoggerFactory.getLogger(DeckController.class);

	
	public DeckController() {
		// TODO Auto-generated constructor stub
	}
	@PostMapping(value = "/create-deck")
	public ResponseEntity<String> createDeck(@RequestBody DeckRequest dr) {
		//System.out.println("creating Deck");
		//System.out.println("deck Idea: "+dr.getDeckIdeaId().toString());
		//System.out.println("deck: "+dr.getDeckId().toString());
		//System.out.println("Key: "+key);
		logger.info("creating deck with deck_id " + dr.getDeckId());
		deckGenerator.makeDeck(dr.getDeckId(),dr.getDeckIdeaId(),key);
		// Create something to 
		
		// CreateCard(Card card, String theme, DeckIdea deckIdea)
		return ResponseEntity.ok("Request received successfully!");
	}
}
