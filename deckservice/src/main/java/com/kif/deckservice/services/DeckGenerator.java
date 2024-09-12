/**
 * 
 */
package com.kif.deckservice.services;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.kif.deckgenmodels.daos.DeckDao;
import com.kif.deckgenmodels.daos.DeckIdeaDao;

import reactor.core.Disposable;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.Deck;
import com.kif.deckgenmodels.DeckIdea;

/**
 * @author Keifer
 *
 */
@Component
public class DeckGenerator {

	@Autowired
	DeckDao deckDao;
	
	@Autowired
	DeckIdeaDao did;

    private static final Logger logger = LoggerFactory.getLogger(DeckGenerator.class);


	// CardGenerator cardGenerator;
	public DeckGenerator(Deck deck, DeckIdea deckIdea) {
		// TODO Auto-generated constructor stub

		// this.cardGenerator = cardGenerator;

	}

	public void makeDeck(String deckId,String deckIdeaId, String key) {
		logger.info("making deck with deck_id " + deckId);

		
		CardService cs = new CardService(WebClient.builder());
		Deck deck = deckDao.findDeckById(deckId);
		DeckIdea deckIdea = did.findByDeckId(deckId);
		System.out.println("theme is: "+deckIdea.getTheme());
		Disposable myMono;
		System.out.println(deck.getCards().toString());
		//Fill out card text detail
		try {
			for (Card card : deck.getCards()) {
				logger.info("Ceating card with card_id "+card.getCardId());
				System.out.println("about to create card: "+card.getName());
				cs.createCard(card.getCardId(), deckIdea.getTheme(), deckIdea.getDeckIdeaId(),key).subscribe(response -> {
					// Handle the response string here.
					if(response.equals("Request received successfully!")) {
						System.out.println("successful");
					}
					//System.out.println("Response: " + response + "... Awesome!");
				});
				
				//System.out.println(myMono.toString());
				
			}
			logger.info("Deck with deck_id "+deckId+" complete");
			deckDao.updateStatusComplete(deckId);
		} catch (Exception e) {
			deckDao.updateStatusFailed(deckId);
			logger.error("Card generation failed");
			e.printStackTrace();
		}

	}

	private String legendManaCost(DeckIdea idea) {
		String cardManaCost = "";
		int colourlessNumber = 0;
		Random rand = new Random();
		int colourCounter = 0;

		if (idea.isWhite()) {
			cardManaCost += "W";
			colourCounter++;
		}
		if (idea.isBlue()) {
			cardManaCost += "U";
			colourCounter++;
		}
		if (idea.isBlack()) {
			cardManaCost += "B";
			colourCounter++;
		}
		if (idea.isRed()) {
			cardManaCost += "R";
			colourCounter++;
		}
		if (idea.isGreen()) {
			cardManaCost += "G";
			colourCounter++;
		}

		colourlessNumber = rand.nextInt(0, 9 - colourCounter);

		if (colourlessNumber != 0) {
			cardManaCost = colourlessNumber + cardManaCost;
		}

		return cardManaCost;
	}

}
