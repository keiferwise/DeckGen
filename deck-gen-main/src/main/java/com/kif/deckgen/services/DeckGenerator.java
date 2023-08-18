/**
 * 
 */
package com.kif.deckgen.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kif.deckgen.daos.CardDao;
import com.kif.deckgen.daos.MinioDao;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.Deck;
import com.kif.deckgenmodels.DeckIdea;
import com.kif.deckgenmodels.Image;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;


/**
 * @author Keifer
 *
 */
@Component
public class DeckGenerator implements Runnable {

	//@Autowired
	//CardGenerator cardGenerator;
	//@Value("${com.kif.generateImages}")
	String makeArt; 
	
	DalleClient dalle;
	
	
	MinioDao minio;
	

	CardComposer composer;
	
	CardDao cardDao;
	/**
	 * 
	 */
	Deck deck;
	DeckIdea deckIdea;
	CardGenerator cardGenerator;
	public DeckGenerator(Deck deck, DeckIdea deckIdea, CardGenerator cardGenerator,CardDao cardDao,MinioDao minioDao, CardComposer cardComposer, DalleClient dalleClient) {
		// TODO Auto-generated constructor stub
		this.deck = deck;
		this.deckIdea = deckIdea;
		this.cardGenerator = cardGenerator;
		this.cardDao = cardDao;
		this.minio = minioDao;
		this.composer = cardComposer;
		this.dalle = dalleClient;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		CardService cs = new CardService(WebClient.builder());
		for(Card card : deck.getCards()) {
			
			//Change this to call the microservice
			cardDao.updateCard(cardGenerator.createCard(card, deck.getName(),deckIdea), card.getCardId());
			System.out.println(card.getCardId());
			cs.createCard(card.getCardId(),deckIdea.getTheme(),deckIdea.getDeckIdeaId()).subscribe(response -> {
			    // Handle the response string here.
			    System.out.println("Response: " + response + "... Awesome!");
			});

		}
		
		/* test microservice */
		
		/*### THIS MEANS WE ARE MAKING ART ###*/
		makeArt="false";
		Card legend = deck.getCards().get(0);
		legend.setName(deckIdea.getLegends());
		legend.setType("Legendary Creature");
		legend.setManaCost(legendManaCost(deckIdea));
		cardDao.save(cardGenerator.createCard(legend, legend.getName(),deckIdea), deck.getDeckId());
	
		deck.setCards(cardDao.findAllByDeckId(deck.getDeckId()));
	    System.out.println("Making art: " + makeArt);

		
		
	
			
			for (Card card : deck.getCards()) {
				
				BufferedImage imgTest =null;
				BufferedImage img = null;
		        URL url=null;
		    	//Generate Art for cards FINISH THIS
				if(makeArt.equals("true")) {
		        

			        
			        //######################################################
					//#### What we will call when we are generating art ####
					//######################################################
					 
					Image art = dalle.generateImage(card.getArtDescription()).getData().get(0);
			        //get the art from Dall-E URL
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
			        
		        
				}
				else {
					// What we will call while we are testing ""
					String artPath = "D:\\deckgen\\deck-gen-main\\src\\main\\resources\\images\\img-H7MTllJItxyHXRMlnO77hB9I.png";
			        try {
						img = ImageIO.read(new File(artPath));
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
				}
		        //Create the card
		        BufferedImage cardImage =null;
		        try {
					 cardImage = composer.createImage(card, img);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
				minio.saveImage(cardImage, card.getCardId());
			}
			
			
		
		
		

	}
	private String legendManaCost(DeckIdea idea) {
		String cardManaCost="";
		int colourlessNumber=0;
		Random rand = new Random();
		int colourCounter=0;
		
		
		if(idea.isWhite()) {cardManaCost+="W"; colourCounter++;}
		if(idea.isBlue()) {cardManaCost+="U"; colourCounter++;}
		if(idea.isBlack()) {cardManaCost+="B"; colourCounter++;}
		if(idea.isRed()) {cardManaCost+="R"; colourCounter++;}
		if(idea.isGreen()) {cardManaCost+="G"; colourCounter++;}

		colourlessNumber = rand.nextInt(0,9-colourCounter);

		if(colourlessNumber != 0) {
			cardManaCost = colourlessNumber+cardManaCost;
		}
		
		return cardManaCost;
	}

}
