package com.kif.deckservice.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.Deck;
import com.kif.deckgenmodels.DeckIdea;
import com.kif.deckgenmodels.services.ChatGPTClient;
/**
 * 
 * @author Keifer
 *
 * TODO This should take a Deck as a parameter, create a deck building job that execute as a different thread and loads the database with the cards one-by-one
 */
@Service
public class CardNamesGenerator {
    private static final Logger logger = LoggerFactory.getLogger(CardNamesGenerator.class);

    @Value("${com.kif.deckListTemplate}")
    private String promptTemplate;
    
    @Autowired
    private ChatGPTClient gptClient;
    
    @Autowired
    private ObjectMapper objectMapper;
   
    
	
public Deck generateCardNames(String inputText, DeckIdea deckIdea) {
		logger.info("Creating card names for deck idea " +deckIdea+ ". " );
		String mana = manaColour(deckIdea);
    	String prompt = promptTemplate.replace("<MYTHEME>", inputText);
    	prompt = prompt.replace("<MANA>", mana);
    	//prompt = promptTemplate.replace("<VIBE>", vibe);
    	String deck = gptClient.generateCompletion(prompt, 1500);
    	Deck deckObject = new Deck();
    	
    	List<Card> cards = null;
		try {
			cards = objectMapper.readValue(deck, new TypeReference<List<Card>>() {}); 
			logger.info("Card names successfully generated");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Card names generation failed due to "+ e.getMessage());
			e.printStackTrace();
		}
    	
    	if(!cards.equals(null)) {
    	
    		deckObject.setCards(cards);
    	}
    	else {
			logger.error("No card names Generated");
    	}
    	
    	
    	
        return deckObject;
    }
    
private String manaColour(DeckIdea idea) {
	
	String result = " ";
	if(idea.isBlack()) {result = result + "Black, ";};
	if(idea.isRed()) {result = result + "Red, ";};
	if(idea.isGreen()) {result = result + "Green, ";};
	if(idea.isWhite()) {result = result + "White, ";};
	if(idea.isBlue()) {result = result + "Blue, ";};
	
	result = result.substring(0, result.length()-2);
	
	System.out.println(result);
	
	
	return result;
}

}
