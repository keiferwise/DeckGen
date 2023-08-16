package com.kif.cardgen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kif.cardgen.daos.CardDao;
import com.kif.cardgen.services.CardGenerator;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.CardRequest;
import com.kif.deckgenmodels.DeckIdea;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CardController {
	@Autowired
	CardDao cardDao;
	@Autowired
	CardGenerator cardGenerator;
	
	public CardController() {
		// TODO Auto-generated constructor stub
	}
	
	@PostMapping("/create-card-for-deck")
	public ResponseEntity<String> createCardForDeck(@RequestBody CardRequest cr) {
		System.out.println("creating card");
		System.out.println(cr.toString());
		// Create something to 
		
		// CreateCard(Card card, String theme, DeckIdea deckIdea)
		return ResponseEntity.ok("Request received successfully!");
	}
	
	@PostMapping("/create-card")
	public ResponseEntity<String> createCard(@RequestBody String requestBody) {
		System.out.println("creating card");
		System.out.println(requestBody.toString());
		cardGenerator.createSingleCard( );
		return ResponseEntity.ok("Request received successfully!");
	}

}
