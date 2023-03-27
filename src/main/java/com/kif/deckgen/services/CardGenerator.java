package com.kif.deckgen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kif.deckgen.models.Card;
import com.kif.deckgen.models.Deck;
import com.kif.deckgen.models.DeckIdea;


/**
 * 
 * @author Keifer
 * 
 * TODO Class with functions that take card names and generate the cards
 */
@Component
public class CardGenerator {

	@Value("${com.kif.cardDetailsTemplate}")
	private String cardDetailsTemplate;
	
	@Autowired
	JsonConverter jsonConverter;
	@Autowired 
	ObjectMapper objectMapper;
	@Autowired
	ChatGPTClient gptClient;
	
	

	
	//TODO take a card object with only a name and type and fill out the details
	public Card createCard(Card card, String theme) {
		String prompt = cardDetailsTemplate.replace("<NAME>", card.getName());
		prompt = prompt.replace("<TYPE>", card.getType());
		prompt = prompt.replace("<THEME>", theme);
		
		String newCardJson = gptClient.generateCompletion(prompt,1000);
		System.out.println(newCardJson);
		//newCardJson = newCardJson.replace("/", "//");
		//Card newCard = jsonConverter.convertCardToObject(newCardJson);
		Card newCard=null;
		try {
			newCard = objectMapper.readValue(newCardJson, Card.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		newCard.setCardId(card.getCardId());
		newCard.setDeckId(card.getDeckId());
		
		return newCard;
	}






}
