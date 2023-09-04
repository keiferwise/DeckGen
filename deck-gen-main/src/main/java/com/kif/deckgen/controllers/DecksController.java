package com.kif.deckgen.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import com.kif.deckgen.daos.MinioDao;
import com.kif.deckgen.services.CardComposer;
import com.kif.deckgen.services.CardGenerator;
import com.kif.deckgen.services.DalleClient;
import com.kif.deckgen.services.DeckGenerator;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.Deck;
import com.kif.deckgenmodels.DeckIdea;


/**
 * This is the controler for decks
 * @author Keifer
 * 
 */
@Controller
public class DecksController {
	@Autowired
	CardDao cardDao;
	@Autowired
	DeckDao deckDao;
	@Autowired
	DeckIdeaDao ideaDao;
	@Autowired 
	MinioDao minio;
	@Autowired
	CardGenerator cardGenerator;
	
	/**
	 * This is for submitting the parameters.
	 * @param currentDeckId
	 * @param model
	 * @return
	 */
	@PostMapping("/submit-deck")
	public String generateDeck(@RequestParam("deckId") String currentDeckId,Model model){
		
		model.addAttribute(model);
		
		model.addAttribute(currentDeckId);

		//Test Deck Idea

		//System.out.println(currentDeckId);
		List<Card> cards = cardDao.findAllByDeckId(currentDeckId);
		Deck deck = deckDao.findDeckById(currentDeckId);
		deck.setCards(cards);
		//ArrayList<Card> finishedCards = new ArrayList<Card>();
		//ArrayList<String> legends = new ArrayList<String>();
		//legends.add("Keifer,Chief Chef");
		DeckIdea idea = ideaDao.findByDeckId(currentDeckId);
		
		//TODO MOVE THIS TO THE MICROSERVICE
		DeckGenerator deckGenerator = new DeckGenerator(deck,idea,cardGenerator,cardDao, new MinioDao(),new CardComposer(),new DalleClient());
		ExecutorService executor = Executors.newSingleThreadExecutor();

		
		executor.execute(deckGenerator);
		
		//Add the finished cards so you can display them.
		//model.addAttribute( finishedCards );
		
		return decks(model);
	}
	
	// this controller shows a list of decks
	/**
	 * List all the decks
	 * @param model
	 * @return
	 */
	@GetMapping("/decks")
	public String decks(Model model) {
		
		List<Deck> myDecks = deckDao.findAll();
		//System.out.println(myDecks.toString());
		model.addAttribute("myDecks",myDecks);
		
		return "decks";
	}
	// 
	/**
	 * Show all the cards in the deck.
	 * this controller lists the cards in the the deck and provides links to each
	 * @param deckId
	 * @param model
	 * @return
	 */
	@GetMapping("/deck/{deckId}")
	public String deck(@PathVariable String deckId, Model model) {
		//System.out.println(deckId);

		List<Card> cards = cardDao.findAllByDeckId(deckId);
		Deck deck = deckDao.findDeckById(deckId);
		model.addAttribute("cards",cards);
		model.addAttribute("deck", deck);
		//System.out.println(cards.isEmpty());
		return "deck";
	}
	/**
	 * 	this controller shows all the cards as images

	 * @param deckId
	 * @param model
	 * @return
	 */
	@GetMapping("/deck/allcards/{deckId}")
	public String deckCardGrid(@PathVariable String deckId, Model model) {
		HashMap<String,String[]> cardMap = new HashMap<String,String[]>(); 
		ArrayList<HashMap<String,String[]>> imageList = new ArrayList<HashMap<String,String[]>>();
		//Deck deck = deckDao.findDeckById(deckId);
		//model.addAttribute("cards",deck.getCards());
		ArrayList<String> images = new ArrayList<String>();
		for(Card c : deckDao.findDeckById(deckId).getCards()) {
			cardMap = new HashMap<String,String[]>();
			cardMap.put("rules", c.getRulesForTemplate("mid").split("<NEWLINE>"));
			cardMap.put("flavor", c.getFlavorText().split("<NEWLINE>"));
			cardMap.put("image", new String[] {minio.getImage(c.getCardId())});
			imageList.add(cardMap);
			images.add(minio.getImage(c.getCardId()));
		}
		
		model.addAttribute("images",images);
		model.addAttribute("imageList",imageList);

		return "card-grid";
	}

}
