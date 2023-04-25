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
import com.kif.deckgen.models.Card;
import com.kif.deckgen.models.Image;
import com.kif.deckgen.models.ImageResult;
import com.kif.deckgen.services.CardComposer;
import com.kif.deckgen.services.DalleClient;

import io.minio.MinioClient;

import com.kif.deckgen.daos.CardDao;
import com.kif.deckgen.daos.MinioDao;



@Controller
public class CardController {

	@Autowired 
	CardDao cardDao;
	
	@Autowired
	DalleClient dalleClient;
	
	@Autowired
	MinioDao minio;
	
	@Autowired 
	CardComposer cardComposer;
	
	@Value("${com.kif.site-title}")
	private String siteTitle;
	
    @GetMapping("/card/{cardId}")
    public String Card(@PathVariable String cardId, Model model) {
    	
    	Card card = cardDao.getCardById(cardId);
    	
    	model.addAttribute(card);
    	//System.out.println(card.getRulesText());
    	Image image = new Image();
    	image.setUrl(minio.getImage(cardId));
    	model.addAttribute("image",image);
        return "card";
    }
    @GetMapping("/card/art/{cardId}")
    public String art(@PathVariable String cardId, Model model) {
    	
    	Card card = cardDao.getCardById(cardId);
    	//System.out.println(card.getArtDescription());
    	//ImageResult result = dalleClient.generateImage(card.getArtDescription());
    	//Image image = result.getData().get(0);
    	
    	//minio.saveImage(image, cardId);
    	//cardComposer.createImage(cardId, cardId, cardId, cardId, cardId, cardId, cardId, cardId, cardId, cardId)
    	//
    	//( mana,  name,  type,  subtype,  rulesText,  flavorText,  power,  toughness,  copywrite,  artist) 
    	Image image = new Image();
    	image.setUrl(minio.getImage(cardId));
    	model.addAttribute("image",image);
    	/*BufferedImage img = null;
    	try {
    		img= cardComposer.createImage(card);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//minio.uploadObject(null);
    	*/
    	
    	return "art";
    }
    
    @GetMapping("/art-test")
    public String artTest(Model model) {
    	//String cardId= UUID.randomUUID().toString();
    	//Card card = cardDao.getCardById(cardId);
    	//System.out.println(card.getArtDescription());
    	//ImageResult result = dalleClient.generateImage(card.getArtDescription());
    	//Image image = new Image();
    	//image.setUrl("https://oaidalleapiprodscus.blob.core.windows.net/private/org-cGoUntEkBIWyFiYN2i9SA6oT/user-vwpIeYuorlrjcgtL57td7628/img-9HgUibWHYnWQjx0pWgS8P5GB.png?st=2023-04-04T22%3A16%3A21Z&se=2023-04-05T00%3A16%3A21Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-04-04T19%3A36%3A05Z&ske=2023-04-05T19%3A36%3A05Z&sks=b&skv=2021-08-06&sig=tQdew%2BeJpXGMfdelgGv4cLkeoSJULj424GaI2JhtW60%3D");
        BufferedImage cardImage =null;
        Card card = cardDao.getCardById("a85a6a42-23dc-4da9-ae82-5e8a40dfe96d");
		BufferedImage imgTest =null;

        String artPath = "D:\\deckgen\\src\\main\\resources\\images\\img-H7MTllJItxyHXRMlnO77hB9I.png";
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

    	//minio.saveImage(image, cardId);
    	
    	model.addAttribute("image",cardImage);
    	
    	//minio.uploadObject(null);
    	
    	
    	return "art-test";
    }
    
	
}
