package com.kif.deckgen.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.kif.deckgen.models.Card;
import com.kif.deckgen.models.Image;
import com.kif.deckgen.models.ImageResult;
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
	
	@Value("${com.kif.site-title}")
	private String siteTitle;
	
    @GetMapping("/card/{cardId}")
    public String Card(@PathVariable String cardId, Model model) {
    	
    	Card card = cardDao.getCardById(cardId);
    	
    	model.addAttribute(card);
    	
        return "card";
    }
    @GetMapping("/card/{cardId}/art")
    public String art(@PathVariable String cardId, Model model) {
    	
    	Card card = cardDao.getCardById(cardId);
    	//System.out.println(card.getArtDescription());
    	ImageResult result = dalleClient.generateImage(card.getArtDescription());
    	Image image = result.getData().get(0);
    	
    	minio.saveImage(image, cardId);
    	
    	model.addAttribute("image",image);
    	
    	//minio.uploadObject(null);
    	
    	
    	return "art";
    }
    
    @GetMapping("/art-test")
    public String artTest(Model model) {
    	String cardId= UUID.randomUUID().toString();
    	//Card card = cardDao.getCardById(cardId);
    	//System.out.println(card.getArtDescription());
    	//ImageResult result = dalleClient.generateImage(card.getArtDescription());
    	Image image = new Image();
    	image.setUrl("https://oaidalleapiprodscus.blob.core.windows.net/private/org-cGoUntEkBIWyFiYN2i9SA6oT/user-vwpIeYuorlrjcgtL57td7628/img-9HgUibWHYnWQjx0pWgS8P5GB.png?st=2023-04-04T22%3A16%3A21Z&se=2023-04-05T00%3A16%3A21Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-04-04T19%3A36%3A05Z&ske=2023-04-05T19%3A36%3A05Z&sks=b&skv=2021-08-06&sig=tQdew%2BeJpXGMfdelgGv4cLkeoSJULj424GaI2JhtW60%3D");
    	
    	minio.saveImage(image, cardId);
    	
    	model.addAttribute("image",image);
    	
    	//minio.uploadObject(null);
    	
    	
    	return "art";
    }
    
	
}
