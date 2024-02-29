package com.kif.deckgen.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kif.deckgen.services.CardComposer;
import com.kif.deckgen.services.CardService;
import com.kif.deckgen.utilities.ManaCostUtility;
import com.kif.deckgenmodels.services.DalleClient;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.Image;
import com.kif.deckgenmodels.ImageResult;
import com.kif.deckgenmodels.daos.CardDao;
import com.kif.deckgenmodels.daos.MinioDao;

import io.minio.MinioClient;


/**
 * This Controller is for viewing cards and the card page.  
 * @author Keifer
 * 
 */
@Controller
public class CardController {

	@Autowired
	CardService cardService;
	
	@Autowired 
	CardDao cardDao;
	
	@Autowired
	DalleClient dalleClient;
	
	@Autowired
	MinioDao minio;
	
	@Autowired 
	CardComposer cardComposer;
	
	@Autowired
	ManaCostUtility manaCostUtility;
	
	@Value("${com.kif.site-title}")
	private String siteTitle;
	
    @GetMapping("/card/{cardId}")
    public String Card(@PathVariable String cardId, Model model) {
    	//cardService.createCard().blockOptional();
    	Card card = cardDao.getCardById(cardId);
    	
    	model.addAttribute(card);
    	//System.out.println(card.getRulesText());
    	Image image = new Image();
    	image.setUrl(minio.getImage(cardId));
    	model.addAttribute("image",image);
    	if(card.getFlavorText()!=null) {
        	model.addAttribute("flavor", card.getFlavorText().split("<NEWLINE>"));
    	}else {
        	model.addAttribute("flavor", "");

    	}
    	if(card.getRulesText()!=null) {
        	model.addAttribute("rules", card.getRulesForTemplate("small").split("<NEWLINE>"));
    	}else {
        	model.addAttribute("rules", "");

    	}
    	model.addAttribute("font-size",card.getTextSize("small"));
    	
    	model.addAttribute("manaCost", manaCostUtility.manaCostForTemplate("Large", card.getManaCost()));
    	
        return "card";
    }
    @GetMapping("/card/art/{cardId}")
    public String art(@PathVariable String cardId, Model model) {
    	

    	Image image = new Image();
    	image.setUrl(minio.getImage(cardId));
    	model.addAttribute("image",image);

    	
    	return "art";
    }
    
    @GetMapping("/card/with-text/{cardId}")
    public String cardWithText(@PathVariable String cardId, Model model) {
    	Card card = cardDao.getCardById(cardId);

    	Image image = new Image();
    	image.setUrl(minio.getImage(cardId));
    	model.addAttribute("image",image);
    	model.addAttribute("flavor", card.getFlavorText().split("<NEWLINE>"));
    	model.addAttribute("rules", card.getRulesText().split("<NEWLINE>"));
    	    	
    	return "card-with-text";
    }
    @GetMapping("/art-test")
    public String artTest(Model model) {
    	
    	BufferedImage cardImage = null;
        Card card = cardDao.getCardById("fb1f9828-c42b-4f37-86a9-a92bc5c57798");
		BufferedImage imgTest =null;
		//TestArt
        String artPath = "D:\\deckgen\\src\\main\\resources\\images\\test-clay.png";
        try {
			imgTest = ImageIO.read(new File(artPath));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        try {
			 cardImage = cardComposer.createImage(card, imgTest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	try {
			ImageIO.write(cardImage, "png", new File("D:\\out-images\\"+ card.getName() + UUID.randomUUID().toString() +".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
    	model.addAttribute("image",cardImage);
    	
    	
    	
    	return "art-test";
    }
    
	
}
