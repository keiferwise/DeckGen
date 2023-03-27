/**
 * 
 */
package com.kif.deckgen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kif.deckgen.daos.CardDao;
import com.kif.deckgen.models.Card;
import com.kif.deckgen.models.Deck;
import com.kif.deckgen.models.DeckIdea;

/**
 * @author Keifer
 *
 */
@Component
public class DeckGenerator implements Runnable {

	//@Autowired
	//CardGenerator cardGenerator;
	
	
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
		Card newCardTemp = new Card();
		
		for(Card card : deck.getCards()) {
			
			//newCardTemp = cardGenerator.createCard(card, deck.getName());
			
			cardDao.updateCard(cardGenerator.createCard(card, deck.getName()), card.getCardId());
			
		}

	}

}
