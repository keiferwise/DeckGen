package com.kif.deckgen.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kif.deckgen.services.CardService;
import com.kif.deckgenmodels.Deck;
import com.kif.deckgenmodels.DeckIdea;

@Controller
public class SingleController {

	@Autowired
	CardService cs;
	
	public SingleController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/new-single")
	public String newSingle() {
		
		return "card-gen";
	}
	
	

    @PostMapping("/submit-card")
    public String makeSingle(
    		@RequestParam("cardName") String name, 
    		@RequestParam("theme") String theme, 
    		@RequestParam("type") String type,
    		@RequestParam("white") Integer white,
    		@RequestParam("blue") Integer blue,
    		@RequestParam("black") Integer black,
    		@RequestParam("red") Integer red,
    		@RequestParam("green") Integer green,
    		@RequestParam("colourless") Integer colourless,
    		@RequestParam("vibe") String vibe,
    		@RequestParam("artStyle") String artStyle,
    		Model model) {
    	System.out.println(name + ", "+ theme + ", "+type + ", "+white + ", "+ blue + ", "+black + ", "+red + ", "+ green+ ", "+ colourless + ", "+ vibe+ ", "+ artStyle);
    	
    	String mana = convertManaToString(white, blue,black,red,green,colourless);
    	System.out.println(mana);
    	cs.createSingle(name, type, theme, artStyle, vibe, mana).subscribe();
        return "decks";
    }
    
    private String convertManaToString(Integer white, Integer blue, Integer black, Integer red, Integer green, Integer colourless) {
    	
    	String mana  = colourless.toString();
    	int counter=0;
    	ArrayList<Integer> wubrg = new ArrayList<Integer>(List.of(white,blue,black,red,green));
    	ArrayList<String> wubrgLables = new ArrayList<String>(List.of("W","U","B","R","G"));
    	
    	for (Integer colour : wubrg){
    		for(int i = 0; i<colour; i++){
    			mana = mana + wubrgLables.get(counter);
    		}
    		counter++;
    	}
    	
    	
    	return "WUBRG";
    }
	
	

}
