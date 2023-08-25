package com.kif.deckservice.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeckController {
	
	public DeckController() {
		// TODO Auto-generated constructor stub
	}
	@PostMapping(value = "/create-deck", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createDeck() {
		
		return ResponseEntity.ok("Request received successfully!");
	}
}
