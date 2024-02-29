package com.kif.deckgen.controllers;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.daos.CardDao;
import com.kif.deckgenmodels.daos.MinioDao;

@Controller
public class BoosterPackController {
	
	@Autowired
	MinioDao minio;
	
	@Autowired
	CardDao cardDao;
	
	public BoosterPackController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/open-booster")
	public String openBooster( Model model) {
		
		ArrayList<Card> cards = (ArrayList<Card>) cardDao.nineRandom();
		HashMap<String,String[]> cardMap = new HashMap<String,String[]>(); 
		ArrayList<HashMap<String,String[]>> imageList = new ArrayList<HashMap<String,String[]>>();

		ArrayList<String> images = new ArrayList<String>();
		for(Card c : cards) {
			cardMap = new HashMap<String,String[]>();
			if((c.getRulesText()!=null)) {
				cardMap.put("rules", c.getRulesForTemplate("mid").split("<NEWLINE>"));
			}
			if((c.getFlavorText()!=null)) {

				cardMap.put("flavor", c.getFlavorText().split("<NEWLINE>"));
			}
			cardMap.put("image", new String[] {minio.getImage(c.getCardId())});
			imageList.add(cardMap);
			images.add(minio.getImage(c.getCardId()));
		}
		
		model.addAttribute("images",images);
		model.addAttribute("imageList",imageList);
		model.addAttribute("cards", cards);
		
		
		return "booster-pack";
	}
	@GetMapping("/booster")
	public String booster( Model model ) {
		
		ArrayList<Card> cards = (ArrayList<Card>) cardDao.nineRandom();
		HashMap<String,String[]> cardMap = new HashMap<String,String[]>(); 
		ArrayList<HashMap<String,String[]>> imageList = new ArrayList<HashMap<String,String[]>>();
		ArrayList<String> images = new ArrayList<String>();
		
		for(Card c : cards) {
			
			cardMap = new HashMap<String,String[]>();
			
			if((c.getRulesText()!=null)) {
				cardMap.put("rules", c.getRulesForTemplate("small").split("<NEWLINE>"));
			}
			
			if((c.getFlavorText()!=null)) {
				cardMap.put("flavor", c.getFlavorText().split("<NEWLINE>"));
			}
			
			cardMap.put("image", new String[] {minio.getImage(c.getCardId())});
			imageList.add(cardMap);
			images.add(minio.getImage(c.getCardId()));
		}
		
		model.addAttribute("images",images);
		model.addAttribute("imageList",imageList);
		model.addAttribute("cards", cards);
		
		
		return "booster";
	}

}
