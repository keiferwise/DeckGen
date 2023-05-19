package com.kif.deckgen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kif.deckgen.models.Card;
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
	ObjectMapper objectMapper;
	@Autowired
	ChatGPTClient gptClient;
	
	@Autowired 
	PromptBuilder pb;
	
	

	
	//TODO take a card object with only a name and type and fill out the details
	public Card createCard(Card card, String theme, DeckIdea deckIdea) {
		System.out.println(cardDetailsTemplate);
		System.out.println("running promptbuilder");
		String prompt = pb.buildCardPrompt(card,deckIdea.getTheme());
		/*String prompt = cardDetailsTemplate.replace("<NAME>", card.getName());
		prompt = prompt.replace("<TYPE>", card.getType());
		prompt = prompt.replace("<THEME>", theme);
		prompt = prompt.replace("<MANACOST>",card.getManaCost());*/
		System.out.println("the prompt is... "+prompt);
		String newCardJson = gptClient.generateCompletion(prompt,2000);
		System.out.println("hello?");
		//System.out.println(newCardJson);
		//newCardJson = newCardJson.replace("/", "//");
		//newCardJson = newCardJson.replace("\n", " ");
		//Card newCard = jsonConverter.convertCardToObject(newCardJson); //System.getProperty("line.separator")
		Card newCard=null;
		try {
			newCard = objectMapper.readValue(newCardJson, Card.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(newCard.toString());
		newCard.setCardId(card.getCardId());
		newCard.setDeckId(card.getDeckId());
		//newCard.setRulesText(newCard.getRulesText().replace("<NEWLINE>", "\n"));
		//newCard.setFlavorText(newCard.getFlavorText().replace("<NEWLINE>", "\n"));
		//System.out.println(card.getName());
		return newCard;
	}
	
	private String manaColour(DeckIdea idea) {
				
		String result = " ";
		if(idea.isBlack()) {result = result + "Black, ";};
		if(idea.isRed()) {result = result + "Red, ";};
		if(idea.isGreen()) {result = result + "Green, ";};
		if(idea.isWhite()) {result = result + "White, ";};
		if(idea.isBlue()) {result = result + "Blue, ";};
		
		result = result.substring(0, result.length()-2);
		
		//System.out.println(result);
		
		
		return result;
	}
	
	private String getCardColours(Card card) {
		String cardColour="";
		//String path = "D:\\deckgen\\src\\main\\resources\\images\\";
		int colourCounter=0;
		//Get Colour Identity String
		if(card.getManaCost().contains("W")) {cardColour+="white, "; colourCounter++;}
		if(card.getManaCost().contains("U")) {cardColour+="blue, "; colourCounter++;}
		if(card.getManaCost().contains("B")) {cardColour+="black, "; colourCounter++;}
		if(card.getManaCost().contains("R")) {cardColour+="red, "; colourCounter++;}
		if(card.getManaCost().contains("G")) {cardColour+="green, "; colourCounter++;}


		//if(colourCounter>1) {
		//	cardColour = "Multicolour";
		//}
		else if(colourCounter == 0) {
			cardColour="Colourless";
		}
		//path = path + frameColour + ".png";

		//System.out.println(path);
		return cardColour.substring(0, cardColour.length()-2);
	}






}
