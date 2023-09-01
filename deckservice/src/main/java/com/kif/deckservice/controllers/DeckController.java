package com.kif.deckservice.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kif.deckgenmodels.CardRequest;
import com.kif.deckgenmodels.DeckRequest;

@RestController
public class DeckController {
	
	public DeckController() {
		// TODO Auto-generated constructor stub
	}
	@PostMapping(value = "/create-deck", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createDeck(@RequestBody DeckRequest cr) {
		System.out.println("creating Deck");
		System.out.println(cr.toString());
		// Create something to 
		
		// CreateCard(Card card, String theme, DeckIdea deckIdea)
		return ResponseEntity.ok("Request received successfully!");
	}
}
