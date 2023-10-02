package com.kif.deckgen.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SingleController {

	public SingleController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/new-single")
	public String newSingle() {
		return "card-gen";
	}
	
	
	

}
