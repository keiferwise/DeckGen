package com.kif.deckgen.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kif.deckgenmodels.User;
import com.kif.deckgenmodels.daos.DeckDao;
import com.kif.deckgenmodels.daos.UserDao;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private DeckDao deckDao;

    /**
     * Displays the form for creating a new user.
     */
    @GetMapping("/create-user")
    public String userForm() {
        logger.info("Accessing user creation form.");
        return "new-user";
    }

    /**
     * Processes the creation of a new user and adds a default deck for the user.
     */
    @PostMapping("/create-user")
    public String createUser(
            Model model,
            @RequestParam("name") String name,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam("admin") boolean admin,
            @RequestParam("enabled") boolean enabled,
            @RequestParam("tokens") Integer tokens) {

        logger.info("Creating user with username: {}, role: {}", username, role);

        // Input validation
        if (name.isBlank() || username.isBlank() || email.isBlank() || password.isBlank()) {
            logger.error("Validation failed: Missing required fields for user creation.");
            model.addAttribute("error", "All fields are required!");
            return "new-user";
        }

        try {
            // Save the user and get the generated user ID
            String userId = userDao.save(username, password, role, email, admin, name, role);

            // Insert a default deck for the new user
            deckDao.insertDefaultDeck(userId);

            logger.info("User created successfully with ID: {}", userId);
        } catch (Exception e) {
            logger.error("Error while creating user: {}", e.getMessage(), e);
            model.addAttribute("error", "Failed to create user. Please try again.");
            return "new-user";
        }

        // Redirect to the user list after successful creation
        return "redirect:/user-list";
    }

    /**
     * Displays a list of all users.
     */
    @GetMapping("/user-list")
    public String userList(Model model) {
        logger.info("Retrieving user list.");
        try {
            List<User> users = userDao.allUsers();
            model.addAttribute("users", users);
            logger.info("Found {} users.", users.size());
        } catch (Exception e) {
            logger.error("Error while retrieving user list: {}", e.getMessage(), e);
            model.addAttribute("error", "Failed to load user list.");
        }

        return "user-list";
    }
}
