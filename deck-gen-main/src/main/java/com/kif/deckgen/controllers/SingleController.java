package com.kif.deckgen.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kif.deckgenmodels.Deck;
import com.kif.deckgenmodels.DeckIdea;

@Controller
public class SingleController {

	public SingleController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/new-single")
	public String newSingle() {
		
		return "card-gen";
	}
	
	

    @PostMapping("/submit-card")
    public String processInput(
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

        return "decks";
    }
	
	

}
