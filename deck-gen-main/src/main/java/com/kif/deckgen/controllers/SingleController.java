package com.kif.deckgen.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kif.deckgenmodels.Card;

@Controller
public class SingleController {

	public SingleController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/new-single")
	public String newSingle() {
		return "Single";
	}
	
	
	

}
