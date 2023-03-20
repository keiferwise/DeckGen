package com.kif.deckgen.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kif.deckgen.models.Deck;
import com.kif.deckgen.repositories.DeckRepository;

//TODO This page should list all the Decks, Eventually filtered by user, should show the status of the deck(Queued, InProgress, Completed)
@Controller
public class DecksController {
	@Autowired
	DeckRepository deckRepo;
	
	@GetMapping("/decks")
	public String decks(Model model) {
		
		List<Deck> myDecks = deckRepo.findAll();
		
		model.addAttribute("myDecks",myDecks);
		
		return "decks";
	}

}
