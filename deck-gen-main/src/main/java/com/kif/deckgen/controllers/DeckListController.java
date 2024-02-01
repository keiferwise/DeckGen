package com.kif.deckgen.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kif.deckgenmodels.services.ChatGPTClient;
import com.kif.deckgenmodels.ArtStyle;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.Deck;
import com.kif.deckgenmodels.DeckIdea;
import com.kif.deckgenmodels.User;
import com.kif.deckgenmodels.daos.ArtStyleDao;
import com.kif.deckgenmodels.daos.CardDao;
import com.kif.deckgenmodels.daos.DeckDao;
import com.kif.deckgenmodels.daos.DeckIdeaDao;
import com.kif.deckgenmodels.daos.MinioDao;
import com.kif.deckgenmodels.daos.UserDao;
import com.kif.deckgen.services.CardNamesGenerator;

@PropertySource("classpath:application.properties")
@Controller
public class DeckListController {

    @Value("${com.kif.site-title}")
    private String siteTitle;
    
    @Autowired
    private CardNamesGenerator cardNamesGenerator;

    @Autowired
    private CardDao cardDao;
    
    @Autowired
    private DeckDao deckDao;
    
    @Autowired
    private MinioDao minioDao;
    
    @Autowired
    private ArtStyleDao artDao;
    
    @Autowired
    private DeckIdeaDao ideaDao;
    
    @Autowired
    private UserDao userDao;
    
    @GetMapping("/")
    public String home() {
    	minioDao.testBucket();
        return "home";
    }
    
    @GetMapping("/deck-gen")
    public String showInputPage(Model model) {
		List<ArtStyle> artStyles = artDao.findAll();	
		model.addAttribute("artStyles",artStyles);

    	//minioDao.testBucket();
        return "deck-gen";
    }


    @PostMapping("/submit-theme")
    public String processInput(
    		@RequestParam("deckName") String name, 
    		@RequestParam("theme") String theme, 
    		@RequestParam("legend") String legend,
    		@RequestParam("mana") String[]	 mana,
    		@RequestParam("vibe") String vibe,
    		@RequestParam("artStyle") String artStyle,
    		Model model) {
		final Authentication currentUserName = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(currentUserName.getName());
		User cu = userDao.findUserByName(currentUserName.getName());
    	if (artStyle.isEmpty() || artStyle.isBlank()) {
        	artStyle = "Virtuosic baroque fantasy Painting";
    	}
    	System.out.println(artStyle);
    	ArrayList<String> manaArray = new ArrayList<String>();
    	Collections.addAll(manaArray, mana);
        UUID deck_idea_id = UUID.randomUUID();
    	UUID deck_id=UUID.randomUUID();
    	DeckIdea deckIdea = new DeckIdea(theme, legend, manaArray.contains("red"), manaArray.contains("green"),manaArray.contains("black"),manaArray.contains("blue"),manaArray.contains("white"),deck_id.toString(),deck_idea_id.toString(),vibe,artStyle);
    	Deck deck = cardNamesGenerator.generateCardNames(theme,deckIdea);
    	ideaDao.save(deckIdea);
    	deck.setDeckId(deck_id.toString());
    	deck.setUser_id(cu.getUserId());
    	deck.setName(name);
    	deckDao.save(deck);
    	cardDao.saveAll(deck.getCards(),deck_id);
    	
    	
        model.addAttribute("cardList", deck.getCards());
        model.addAttribute("deck",deck);
        //model.addAttribute("inputText", deck);

        return "deck-list";
    }
   
    
}
