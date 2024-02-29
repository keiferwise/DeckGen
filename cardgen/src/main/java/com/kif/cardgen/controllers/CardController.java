package com.kif.cardgen.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
		nc = cardGenerator.createSingleCard(sr,newCardId);
		cardDao.save(nc,sr.getDeckId(),newCardId); 
		return ResponseEntity.ok(newCardId);
	}

}
