/**
 * 
 */
package com.kif.deckgen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kif.deckgen.daos.CardDao;
import com.kif.deckgen.daos.MinioDao;
import com.kif.deckgen.models.Card;
import com.kif.deckgen.models.Deck;
import com.kif.deckgen.models.DeckIdea;
import com.kif.deckgen.models.Image;

/**
 * @author Keifer
 *
 */
@Component
public class DeckGenerator implements Runnable {

	//@Autowired
	//CardGenerator cardGenerator;
	@Value("${com.kif.generateImages}")
	boolean makeArt; 
	@Autowired
	DalleClient dalle;
	
	@Autowired
	MinioDao minio;
	
	CardDao cardDao;
	/**
	 * 
	 */
	Deck deck;
	DeckIdea deckIdea;
	CardGenerator cardGenerator;
	public DeckGenerator(Deck deck, DeckIdea deckIdea, CardGenerator cardGenerator,CardDao cardDao) {
		// TODO Auto-generated constructor stub
		this.deck = deck;
		this.deckIdea = deckIdea;
		this.cardGenerator = cardGenerator;
		this.cardDao = cardDao;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Card newCardTemp = new Card();
		
		for(Card card : deck.getCards()) {
			
			//newCardTemp = cardGenerator.createCard(card, deck.getName());
			
			cardDao.updateCard(cardGenerator.createCard(card, deck.getName(),deckIdea), card.getCardId());
			
		}
		
		Card legend = deck.getCards().get(0);
		legend.setName(deckIdea.getLegends());
		legend.setType("Legendary Creature");

		cardDao.updateCard(cardGenerator.createCard(legend, deck.getName(),deckIdea), legend.getCardId());
		
		//deckIdea.getLegends();
		deck.setCards(cardDao.findAllByDeckId(deck.getDeckId()));
		
		
		//Generate Art for cards FINISH THIS
		if(makeArt==true) {
			
			for (Card card : deck.getCards()) {
				
				//dalle.generateImage(card.getArtDescription()).getData().get(0).getUrl();
				
				Image art = dalle.generateImage(card.getArtDescription()).getData().get(0);
				
				
				
				//minio.saveImage(, card.getCardId());
			}
			
			
		}
		
		

	}

}
