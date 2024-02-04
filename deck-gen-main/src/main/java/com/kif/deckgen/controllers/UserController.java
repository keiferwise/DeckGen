package com.kif.deckgen.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kif.deckgenmodels.User;
import com.kif.deckgenmodels.daos.UserDao;
@Controller
public class UserController {
	
	@Autowired
	UserDao userDao;

	public UserController() {

	}
	
	@GetMapping("/create-user")
	public String userForm() {
		
		return "new-user";
		
	}
	@PostMapping("/create-user")
	public String createUser(Model model,
			@RequestParam("name") String name, 
    		@RequestParam("username") String usernam, 
    		@RequestParam("email") String email,
    		@RequestParam("password") String password,
    		@RequestParam("role") String role,
    		@RequestParam("admin") boolean admin,
    		@RequestParam("enabled") boolean enabled, 
			@RequestParam("tokens") Integer tokens)
	{
		
		
		userDao.save(usernam, password, role, email, admin, name);
		return "/user-list";
	}
	@GetMapping("/user-list")
	public String userList(Model model) {
		ArrayList<User> users = new ArrayList<User>();
		model.addAttribute(users);
		userDao.allUsers();
		
		return "user-list";
	}

}
