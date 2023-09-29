package com.kif.deckgen.services;

import java.util.List;

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
	
    @Value("${com.kif.deckListTemplate}")
    private String promptTemplate;
    
    @Autowired
    private ChatGPTClient gptClient;
    
    @Autowired
    private ObjectMapper objectMapper;
   
    
	
public Deck generateCardNames(String inputText, DeckIdea deckIdea) {
    	
    	//ChatGPTClient gptClient = new ChatGPTClient();
		String mana = manaColour(deckIdea);
    	String prompt = promptTemplate.replace("<MYTHEME>", inputText);
    	prompt = prompt.replace("<MANA>", mana);
    	String deck = gptClient.generateCompletion(prompt, 1500);
    	Deck deckObject = new Deck();
    	
    	List<Card> cards = null;
		try {
			cards = objectMapper.readValue(deck, new TypeReference<List<Card>>() {}); //THIS IS A PROBLEM, I THINK I AM DESERIALIZING THE CARD BUT I AM NOT
			System.out.println(cards.get(0).getClass());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(!cards.equals(null)) {
    		//deckObject.setCards(cards);
    		//deckObject.getCards().add( objectMapper.readValue(cards.get(0), Card.class) );
    		deckObject.setCards(cards);

    		System.out.println(deckObject.getCards().get(0).getClass());
    		System.out.println(deckObject.getDeckId());
    		//for(Card c : deckObject.getCards()){
    		//	c.setDeckId(deckObject.getDeckId());
    		//}
    		//deckRepo.save(deckObject);
    	}
    	else {
    		System.out.println("fuck you");
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
