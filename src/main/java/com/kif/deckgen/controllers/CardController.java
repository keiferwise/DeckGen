package com.kif.deckgen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.kif.deckgen.models.Card;

import com.kif.deckgen.daos.CardDao;



@Controller
public class CardController {

	@Autowired 
	CardDao cardDao;
	
	@Value("${com.kif.site-title}")
	private String siteTitle;
	
    @GetMapping("/card/{cardId}")
    public String Card(@PathVariable String cardId, Model model) {
    	
    	Card card = cardDao.getCardById(cardId);
    	
    	model.addAttribute(card);
    	
        return "card";
    }
	
}
