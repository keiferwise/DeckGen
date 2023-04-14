package com.kif.deckgen.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import com.kif.deckgen.daos.CardDao;
import com.kif.deckgen.daos.DeckDao;
import com.kif.deckgen.daos.DeckIdeaDao;
import com.kif.deckgen.daos.MinioDao;
import com.kif.deckgen.models.Card;
import com.kif.deckgen.models.Deck;
import com.kif.deckgen.models.DeckIdea;
import com.kif.deckgen.services.ChatGPTClient;
import com.kif.deckgen.services.CardNamesGenerator;

@PropertySource("classpath:application.properties")
@Controller
public class DeckListController {

    @Value("${com.kif.site-title}")
    private String siteTitle;
    
    /*@Value("${com.kif.test-json}")
    private String testJson;*/
    
    @Autowired
    private CardNamesGenerator cardNamesGenerator;

    @Autowired
    private CardDao cardDao;
    
    @Autowired
    private DeckDao deckDao;
    
    @Autowired
    private MinioDao minioDao;
    
    
    @Autowired
    private DeckIdeaDao ideaDao;
    
    @GetMapping("/")
    public String showInputPage() {
    	minioDao.testBucket();
        return "deck-gen";
    }


    @PostMapping("/submit-theme")
    public String processInput(
    		@RequestParam("deckName") String name, 
    		@RequestParam("theme") String theme, 
    		@RequestParam("legend") String legend,
    		@RequestParam("mana") String[]	 mana,
    		@RequestParam("vibe") String vibe,
    		Model model) {
    	
    	ArrayList<String> manaArray = new ArrayList<String>();
    	Collections.addAll(manaArray, mana);
    	Deck deck = cardNamesGenerator.generateCardNames(theme);// TODO Add other parameters
        UUID deck_idea_id = UUID.randomUUID();
    	UUID deck_id=UUID.randomUUID();
    	DeckIdea deckIdea = new DeckIdea(theme, legend, manaArray.contains("red"), manaArray.contains("green"),manaArray.contains("black"),manaArray.contains("blue"),manaArray.contains("white"),deck_id.toString(),deck_idea_id.toString());
    	ideaDao.save(deckIdea);
    	deck.setDeckId(deck_id.toString());
    	deck.setName(name);
    	deckDao.save(deck);
    	cardDao.saveAll(deck.getCards(),deck_id);
    	System.out.println(vibe);
    	
        model.addAttribute("inputText", deck.getCards().toString());
        model.addAttribute("deck",deck);
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
    /*
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
    */
    
}
