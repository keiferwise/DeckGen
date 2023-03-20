package com.kif.deckgen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kif.deckgen.models.Card;
import com.kif.deckgen.models.Deck;

@Service
public class JsonConverter {
	
	@Autowired
	ObjectMapper mapper;
	
	//TODO 
	public Deck convertDeckToObject(String deckJson) {
		return null;
		
	}
	//TODO
	public String convertDeckToJson(Deck deck) {
		return null;
	}
	//TODO
	public Card convertCardToObject(String cardJson) {
		
		return null;
	
	}
	//TODO
	public String convertCardToJson(Card card) {
		
		return null;
	}

}
