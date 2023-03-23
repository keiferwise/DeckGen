package com.kif.deckgen.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kif.deckgen.daos.CardDao;
import com.kif.deckgen.daos.DeckDao;
import com.kif.deckgen.models.Card;
import com.kif.deckgen.models.Deck;
import com.kif.deckgen.services.ChatGPTClient;

@PropertySource("classpath:application.properties")
@Controller
public class DeckListController {
    @Value("${com.kif.deckListTemplate}")
    private String promptTemplate;
    @Value("${com.kif.site-title}")
    private String siteTitle;
    
    /*@Value("${com.kif.test-json}")
    private String testJson;*/
    
    @Autowired
    private ChatGPTClient gptClient;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private CardDao cardDao;
    
    @Autowired
    private DeckDao deckDao;
    
  
    
    @GetMapping("/deck-gen")
    public String showInputPage() {
        //System.out.println(siteTitle);
        //System.out.println("hello");
    	//cardDao.count();
        return "deck-gen";
    }


    @PostMapping("/submit-theme")
    public String processInput(@RequestParam("inputText") String inputText, Model model) {
    	Deck deck = generateCardNames(inputText);
        

    	deck.setDeckId(UUID.randomUUID().toString());
    	deckDao.save(deck);
    	
        model.addAttribute("inputText", deck.getCards().toString());
        //model.addAttribute("inputText", deck);


        return "deck-list";
       // return "redirect:/result"
    }
    //
   /*
    @GetMapping("/deck-list")
    public String showResultPage(Model model) {
        //model.addAttribute("transformedText", transformedText);
        return "deck-list";
    }
    */
    
    private Deck generateCardNames(String inputText) {
    	
    	//ChatGPTClient gptClient = new ChatGPTClient();
    	String prompt = promptTemplate.replace("<MYTHEME>", inputText);
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
    		deckObject.getCards().add( cards.get(0) );

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
    	
    	
    	
        // perform some transformation on the input (e.g. convert to uppercase)
        return deckObject;
    }
    
    
    @GetMapping("/deck-list-test")
    public String testJson(Model model) {
    	String testJson="[{\"name\": \"Strawberry Mage\", \"type\": \"Creature\"}, {\"name\": \"Strawberry Summoner\", \"type\": \"Creature\"}]";
        
    	Deck myDeck =  new Deck();
    	ObjectMapper myOM = new ObjectMapper();
    	List<Card> myCards=null;
		try {
			myCards = myOM.readValue(testJson, new TypeReference<List<Card>>() {});
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
    	
    	myDeck.setCards(myCards);
		// deckRepo.save(myDeck);

    	System.out.println(myDeck.getCards().toString());
    	
    	model.addAttribute("transformedText", testJson);
        return "deck-list";
    }
    
}
