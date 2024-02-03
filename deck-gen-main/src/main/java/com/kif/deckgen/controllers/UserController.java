package com.kif.deckgen.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class UserController {

	public UserController() {

	}
	
	@GetMapping("/create-user")
	public String userForm() {
		
		return "new-user";
		
	}
	@PostMapping("/create-user")
	public String createUser(Model model) 
	{
		
		return "home";
	}
	@GetMapping("/user-list")
	public String userList() {
		return "user-list";
	}

}
