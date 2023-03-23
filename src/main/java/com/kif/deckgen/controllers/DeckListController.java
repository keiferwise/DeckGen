package com.kif.deckgen.controllers;

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
import com.kif.deckgen.services.DeckGenerator;

@PropertySource("classpath:application.properties")
@Controller
public class DeckListController {

    @Value("${com.kif.site-title}")
    private String siteTitle;
    
    /*@Value("${com.kif.test-json}")
    private String testJson;*/
    
    @Autowired
    private DeckGenerator deckGenerator;


    @Autowired
    private CardDao cardDao;
    
    @Autowired
    private DeckDao deckDao;
    
  
    
    @GetMapping("/deck-gen")
    public String showInputPage() {
        return "deck-gen";
    }


    @PostMapping("/submit-theme")
    public String processInput(@RequestParam("inputText") String inputText, Model model) {
    	Deck deck = deckGenerator.generateCardNames(inputText);
        UUID myUuid=UUID.randomUUID();

    	deck.setDeckId(myUuid.toString());
    	deck.setName(inputText);
    	deckDao.save(deck);
    	cardDao.saveAll(deck.getCards(),myUuid);
    	
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
