/**
 * 
 */
package com.kif.deckservice.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.kif.deckgenmodels.daos.DeckDao;
import com.kif.deckgenmodels.daos.DeckIdeaDao;
import com.kif.deckservice.services.CardService;

import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.Deck;
import com.kif.deckgenmodels.DeckIdea;

/**
 * @author Keifer
 *
 */
@Component
public class DeckGenerator {

	// @Autowired
	// CardGenerator cardGenerator;
	// @Value("${com.kif.generateImages}")

	@Autowired
	DeckDao deckDao;
	
	@Autowired
	DeckIdeaDao did;
	// MinioDao minio;

	// CardComposer composer;

	// CardDao cardDao;
	/**
	 * 
	 */


	// CardGenerator cardGenerator;
	public DeckGenerator(Deck deck, DeckIdea deckIdea) {
		// TODO Auto-generated constructor stub

		// this.cardGenerator = cardGenerator;

	}

	public void makeDeck(String deckId,String deckIdeaId) {
		System.out.println("making deck: " + deckId);
		// TODO Auto-generated method stub
		// CardService cs = new CardService(WebClient.builder());
		CardService cs = new CardService(WebClient.builder());
		Deck deck = deckDao.findDeckById(deckId);
		DeckIdea deckIdea = did.findByDeckId(deckId);
		Disposable myMono;
		//Fill out card text detail
		try {
			for (Card card : deck.getCards()) {
			
				
				cs.createCard(card.getCardId(), deckIdea.getTheme(), deckIdea.getDeckIdeaId()).subscribe(response -> {
					// Handle the response string here.
					if(response.equals("Request received successfully!")) {
						
					}
					//System.out.println("Response: " + response + "... Awesome!");
				});
				
				//System.out.println(myMono.toString());
				
			}
			deckDao.updateStatusComplete(deckId);
		} catch (Exception e) {
			// TODO Auto-generated catch 
			deckDao.updateStatusFailed(deckId)	;
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
