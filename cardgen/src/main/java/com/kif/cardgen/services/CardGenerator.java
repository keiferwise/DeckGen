package com.kif.cardgen.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.DeckIdea;
import com.kif.deckgenmodels.Image;
import com.kif.deckgenmodels.ImageResult;
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
    private static final Logger logger = LoggerFactory.getLogger(CardGenerator.class);

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
		
		try {
			cardDao.updateCard(cardWithAllText, cardWithAllText.getCardId());
			// call the card composer to make the art.
			createCardArt(cardWithAllText,ideaDao.findByDeckIdeaId(deckIdeaId).getArtStyle());
			cardDao.updateStatusComplete(cardid);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			cardDao.updateStatusFailed(cardid);

			e.printStackTrace();
		}

		
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
			ImageResult ir = dalle.generateImage(card.getArtDescription() + ". In this art style: " +artStyle );
			if(ir == null) {
				cardDao.updateStatusFailed(card.getCardId());
				return 0;
			}
			Image art = ir.getData().get(0);
			
			
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
		card.setSubtype(sr.getSubType());
		idea.setArtStyle(sr.getArtStyle());
		idea.setTheme(sr.getTheme());
		idea.setVibe(sr.getVibe());
		idea.setDeckIdeaId("none");
		String prompt = pb.buildCardPrompt(card, idea);
		
		return  sendPromptToGpt(prompt);

	}
	
	private Card sendPromptToGpt(String prompt) {
		String newCardJson = gptClient.generateCompletion(prompt, 2000);
		//String newCardJson = chatApiClient.chat(prompt);
		// System.out.println(newCardJson);

		Card newCard = new Card();
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