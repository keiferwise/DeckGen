package com.kif.deckgen.controllers;

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
    	
    	
    	
    	model.addAttribute("image",image);
    	
    	//minio.uploadObject(null);
    	
    	
    	return "art";
    }
	
}
