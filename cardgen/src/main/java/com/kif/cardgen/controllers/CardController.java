package com.kif.cardgen.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CardController {

	public CardController() {
		// TODO Auto-generated constructor stub
	}
	
	@PostMapping("/create-card")
	public ResponseEntity<String> createCard(@RequestBody String requestBody) {
		System.out.println("creating card");
		System.out.println(requestBody.toString());

		return ResponseEntity.ok("Request received successfully!");
	}

}
