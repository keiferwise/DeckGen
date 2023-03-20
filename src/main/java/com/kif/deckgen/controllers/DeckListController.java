package com.kif.deckgen.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    
    @GetMapping("/deck-gen")
    public String showInputPage() {
        //System.out.println(siteTitle);
        //System.out.println("hello");
        return "deck-gen";
    }
    @Autowired
    private ChatGPTClient gptClient;

    @PostMapping("/submit-theme")
    public String processInput(@RequestParam("inputText") String inputText, Model model) {
    	String deck = transformInput(inputText);
        model.addAttribute("inputText", deck);
        model.addAttribute("inputText", deck);


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
    
    private String transformInput(String inputText) {
    	
    	//ChatGPTClient gptClient = new ChatGPTClient();
    	String prompt = promptTemplate.replace("<MYTHEME>", inputText);
    	String deck = gptClient.generateCompletion(prompt, 500);
        // perform some transformation on the input (e.g. convert to uppercase)
        return deck;
    }
    
    
    @GetMapping("/deck-list-test")
    public String testJson(Model model) {
    	String testJson="[{\"name\": \"Strawberry Mage\", \"type\": \"Creature\"}, {\"name\": \"Strawberry Summoner\", \"type\": \"Creature\"}]";
        
    	Deck myDeck =  new Deck();
    	ObjectMapper myOM = new ObjectMapper();
    	ArrayList<Card> myCards=null;
		try {
			myCards = myOM.readValue(testJson, ArrayList.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
    	
    	myDeck.setCards(myCards);
    
    	System.out.println(myDeck.getCards().toString());
    	
    	model.addAttribute("transformedText", testJson);
        return "deck-list";
    }
    
}
