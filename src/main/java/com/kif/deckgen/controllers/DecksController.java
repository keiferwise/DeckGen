package com.kif.deckgen.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kif.deckgen.daos.CardDao;
import com.kif.deckgen.daos.DeckDao;
import com.kif.deckgen.daos.DeckIdeaDao;
import com.kif.deckgen.models.Card;
import com.kif.deckgen.models.Deck;
import com.kif.deckgen.models.DeckIdea;
import com.kif.deckgen.services.CardGenerator;
import com.kif.deckgen.services.DeckGenerator;

//TODO This page should list all the Decks, Eventually filtered by user, should show the status of the deck(Queued, InProgress, Completed)
@Controller
public class DecksController {
	@Autowired
	CardDao cardDao;
	@Autowired
	DeckDao deckDao;
	@Autowired
	DeckIdeaDao ideaDao;
	
	@Autowired
	CardGenerator cardGenerator;
	
	//@Autowired
	//DeckGenerator deckGenerator;
	
	@PostMapping("/submit-deck")
	public String generateDeck(@RequestParam("deckId") String currentDeckId,Model model){
		
		model.addAttribute(model);
		
		model.addAttribute(currentDeckId);

		//Test Deck Idea

		System.out.println(currentDeckId);
		List<Card> cards = cardDao.findAllByDeckId(currentDeckId);
		Deck deck = deckDao.findDeckById(currentDeckId);
		deck.setCards(cards);
		ArrayList<Card> finishedCards = new ArrayList<Card>();
		ArrayList<String> legends = new ArrayList<String>();
		legends.add("Keifer,Chief Chef");
		DeckIdea idea = ideaDao.findByDeckId(currentDeckId);
		
		//TODO Generate the deck
		DeckGenerator deckGenerator = new DeckGenerator(deck,idea,cardGenerator,cardDao);
		
		//deckGenerator.run();
		ExecutorService executor = Executors.newSingleThreadExecutor();

		
		executor.execute(deckGenerator);
		
		//Add the finished cards so you can display them.
		model.addAttribute( finishedCards );
		
		return "deck";
	}
	
	
	@GetMapping("/decks")
	public String decks(Model model) {
		
		List<Deck> myDecks = deckDao.findAll();
		//System.out.println(myDecks.toString());
		model.addAttribute("myDecks",myDecks);
		
		return "decks";
	}
	
	@GetMapping("/deck/{deckId}")
	public String deck(@PathVariable String deckId, Model model) {
		//System.out.println(deckId);

		List<Card> cards = cardDao.findAllByDeckId(deckId);
		
		model.addAttribute("cards",cards);
		//System.out.println(cards.isEmpty());
		return "deck";
	}

}
