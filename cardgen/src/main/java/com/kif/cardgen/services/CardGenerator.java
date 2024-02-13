package com.kif.cardgen.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.DeckIdea;
import com.kif.deckgenmodels.Image;
import com.kif.deckgenmodels.SingleRequest;
import com.kif.deckgenmodels.services.ChatApiClient;
import com.kif.deckgenmodels.services.ChatGPTClient;
import com.kif.deckgenmodels.services.DalleClient;

import com.kif.deckgenmodels.daos.CardDao;
import com.kif.deckgenmodels.daos.DeckIdeaDao;
import com.kif.deckgenmodels.daos.MinioDao;



/**
 * 
 * @author Keifer
 * 
 *         TODO Class with functions that take card names and generate the cards
 */
@Component
public class CardGenerator {

	@Value("${com.kif.cardDetailsTemplate}")
	private String cardDetailsTemplate;

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	ChatGPTClient gptClient;
	@Autowired
	ChatApiClient chatApiClient;
	@Autowired
	PromptBuilder pb;
	@Autowired
	CardDao cardDao;
	@Autowired
	DeckIdeaDao ideaDao;
	@Autowired
	CardComposer composer;
	@Autowired
	DalleClient dalle;
	@Autowired
	MinioDao minio;
	
	// TODO take a card object with only a name and type and fill out the details
	public Card createCard(String cardid, String theme, String deckIdeaId) {
		
		 
		
		//  Call createCardText method 
		Card cardWithAllText = createCardText(cardid, theme, deckIdeaId);
		System.out.println("This is the card we are about to update"+cardWithAllText.toString());
		cardDao.updateCard(cardWithAllText, cardWithAllText.getCardId());
			
		// call the card composer to make the art.
		createCardArt(cardWithAllText,ideaDao.findByDeckIdeaId(deckIdeaId).getArtStyle());

		
		
		return cardWithAllText;
	}

	
	private Card createCardText(String cardid, String theme, String deckIdeaId) {
		Card card = cardDao.getCardById(cardid);
		DeckIdea deckIdea = ideaDao.findByDeckIdeaId(deckIdeaId);
		Card newCard = null;

		
		
		String prompt = pb.buildCardPrompt(card, deckIdea);

		newCard = sendPromptToGpt(prompt);
	
		System.out.println(newCard.toString());
		newCard.setCardId(card.getCardId());
		newCard.setDeckId(card.getDeckId());
		newCard.setStatus("INPROGRESS");
		// System.out.println(card.getName());
		return newCard;
	}

	
	private int createCardArt(Card card, String artStyle) {
		/* ### THIS decides if WE ARE MAKING ART ### */
		String makeArt = "true";

		System.out.println("Making art: " + makeArt);

		BufferedImage imgTest = null;
		BufferedImage img = null;
		URL url = null;
		// Generate Art for cards FINISH THIS
		if (makeArt.equals("true")) {

			// ######################################################
			// #### What we will call when we are generating art ####
			// ######################################################

			Image art = dalle.generateImage(card.getArtDescription() + ". In this art style: " +artStyle ).getData().get(0);
			// get the art from Dall-E URL
			try {
				url = new URL(art.getUrl());
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				cardDao.updateStatusFailed(card.getCardId());
				e1.printStackTrace();
			}
			try {
				img = ImageIO.read(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				cardDao.updateStatusFailed(card.getCardId());
				e.printStackTrace();
			}

		} else {
			// What we will call while we are testing ""
			String artPath = "D:\\deckgen\\cardgen\\src\\main\\resources\\images\\test-clay.png";
			try {
				img = ImageIO.read(new File(artPath));//
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				cardDao.updateStatusFailed(card.getCardId());
				e2.printStackTrace();
			}

		}
		// Create the card
		BufferedImage cardImage = null;
		try {
			cardImage = composer.createImage(card, img);
			cardDao.updateStatusComplete(card.getCardId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			cardDao.updateStatusFailed(card.getCardId());

			e.printStackTrace();
		}

		return minio.saveImage(cardImage, card.getCardId());

	}
	
	public Card createSingleCard(SingleRequest sr,String cardId) {
		Card newCard = createSingleCardText(sr);
		newCard.setCardId(cardId);
		createCardArt(newCard,sr.getArtStyle());
		return newCard;
		
	}

	public Card createSingleCardText(SingleRequest sr) {
		// TODO Auto-generated method stub
		
		Card card = new Card();
		DeckIdea idea = new DeckIdea();
		card.setName(sr.getName());
		card.setManaCost(sr.getMana());
		card.setType(sr.getType());
		idea.setArtStyle(sr.getArtStyle());
		idea.setTheme(sr.getTheme());
		idea.setVibe(sr.getVibe());
		String prompt = pb.buildCardPrompt(card, idea);
		
		return  sendPromptToGpt(prompt);

	}
	
	private Card sendPromptToGpt(String prompt) {
		String newCardJson = gptClient.generateCompletion(prompt, 2000);
		//String newCardJson = chatApiClient.chat(prompt);
		// System.out.println(newCardJson);

		Card newCard = null;
		try {
			newCard = objectMapper.readValue(newCardJson, Card.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			newCard.setStatus("FAILED");
			e.printStackTrace();
		}
		System.out.println(newCard.toString());
		//newCard.setCardId(card.getCardId());
		//newCard.setDeckId(card.getDeckId());
		// System.out.println(card.getName());
		return newCard;
		
	}
}
//THERE IS THREE METHODS WE DON'T SEEM TO BE USING HERE
	/* This translates the boolean fields in the deckidea into a human/LLM readable list */
	/*private String manaColour(DeckIdea idea) {

		String result = " ";
		if (idea.isBlack()) {
			result = result + "Black, ";
		}
		;
		if (idea.isRed()) {
			result = result + "Red, ";
		}
		;
		if (idea.isGreen()) {
			result = result + "Green, ";
		}
		;
		if (idea.isWhite()) {
			result = result + "White, ";
		}
		;
		if (idea.isBlue()) {
			result = result + "Blue, ";
		}
		;

		result = result.substring(0, result.length() - 2);

		return result;
	}*/

	/*private String getCardColours(Card card) {
		String cardColour = "";
		// String path = "D:\\deckgen\\src\\main\\resources\\images\\";
		int colourCounter = 0;
		// Get Colour Identity String
		if (card.getManaCost().contains("W")) {
			cardColour += "white, ";
			colourCounter++;
		}
		if (card.getManaCost().contains("U")) {
			cardColour += "blue, ";
			colourCounter++;
		}
		if (card.getManaCost().contains("B")) {
			cardColour += "black, ";
			colourCounter++;
		}
		if (card.getManaCost().contains("R")) {
			cardColour += "red, ";
			colourCounter++;
		}
		if (card.getManaCost().contains("G")) {
			cardColour += "green, ";
			colourCounter++;
		}

		// if(colourCounter>1) {
		// cardColour = "Multicolour";
		// }
		else if (colourCounter == 0) {
			cardColour = "Colourless";
		}
		// path = path + frameColour + ".png";

		// System.out.println(path);
		return cardColour.substring(0, cardColour.length() - 2);
	}

	*/

	/* This Generates a manacost to use for the Legend*/
	/*
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
*/

/* OLD CODE AS REFRENCE FOR IF WE HAVE ISSUE LATER
Card card = cardDao.getCardById(cardid);
DeckIdea deckIdea = ideaDao.findByDeckId(deckIdeaId);

String prompt = pb.buildCardPrompt(card, deckIdea);

// System.out.println("the prompt is... "+prompt);
String newCardJson = gptClient.generateCompletion(prompt, 2000);
// System.out.println(newCardJson);

Card newCard = null;
try {
	newCard = objectMapper.readValue(newCardJson, Card.class);
} catch (JsonProcessingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
System.out.println(newCard.toString());
newCard.setCardId(card.getCardId());
newCard.setDeckId(card.getDeckId());
// System.out.println(card.getName());

// ### THIS decides if WE ARE MAKING ART ### 
String makeArt = "false";

System.out.println("Making art: " + makeArt);

BufferedImage imgTest = null;
BufferedImage img = null;
URL url = null;
// Generate Art for cards FINISH THIS
if (makeArt.equals("true")) {

	// ######################################################
	// #### What we will call when we are generating art ####
	// ######################################################

	Image art = dalle.generateImage(card.getArtDescription()).getData().get(0);
	// get the art from Dall-E URL
	try {
		url = new URL(art.getUrl());
	} catch (MalformedURLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		img = ImageIO.read(url);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

} else {
	// What we will call while we are testing ""
	String artPath = "D:\\deckgen\\deck-gen-main\\src\\main\\resources\\images\\img-H7MTllJItxyHXRMlnO77hB9I.png";
	try {
		img = ImageIO.read(new File(artPath));
	} catch (IOException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}

}
// Create the card
BufferedImage cardImage = null;
try {
	cardImage = composer.createImage(card, img);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

minio.saveImage(cardImage, card.getCardId());
 */
