package com.kif.deckgen.controllers;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kif.deckgenmodels.*;
import com.kif.deckgenmodels.daos.*;
import com.kif.deckgen.services.CardNamesGenerator;

@PropertySource("classpath:application.properties")
@Controller
public class DeckListController {

    private static final Logger logger = LoggerFactory.getLogger(DeckListController.class);

    @Value("${com.kif.site-title}")
    private String siteTitle;

    @Autowired
    private CardNamesGenerator cardNamesGenerator;

    @Autowired
    private CardDao cardDao;

    @Autowired
    private DeckDao deckDao;

    @Autowired
    private MinioDao minioDao;

    @Autowired
    private ArtStyleDao artDao;

    @Autowired
    private DeckIdeaDao ideaDao;

    @Autowired
    private UserDao userDao;

    /**
     * Displays the home page.
     */
    @GetMapping("/")
    public String home() {
        logger.info("Accessing home page");
        minioDao.testBucket();
        return "home";
    }

    /**
     * Displays the deck generation input page.
     */
    @GetMapping("/deck-gen")
    public String showInputPage(Model model) {
        logger.info("Accessing deck generation page");
        List<ArtStyle> artStyles = artDao.findAll();
        model.addAttribute("artStyles", artStyles);
        return "deck-gen";
    }

    /**
     * Processes the input from the deck generation form and generates a new deck.
     *
     * @param name     the name of the deck
     * @param theme    the theme of the deck
     * @param legend   the legend for the deck
     * @param mana     the mana colors selected
     * @param vibe     the vibe description
     * @param artStyle the art style for the deck
     * @param model    the model to pass attributes to the view
     * @return the view name to render
     */
    @PostMapping("/submit-theme")
    public String processInput(
            @RequestParam("deckName") String name,
            @RequestParam("theme") String theme,
            @RequestParam("legend") String legend,
            @RequestParam("mana") String[] mana,
            @RequestParam("vibe") String vibe,
            @RequestParam("artStyle") String artStyle,
            Model model) {

        // Log input data
        logger.info("Processing theme submission with name: {}, theme: {}, legend: {}", name, theme, legend);

        // Retrieve the current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            logger.error("Unauthorized access attempt");
            return "redirect:/login";
        }

        String currentUsername = authentication.getName();
        logger.info("Authenticated user: {}", currentUsername);

        User currentUser = userDao.findUserByName(currentUsername);
        if (currentUser == null) {
            logger.error("User not found: {}", currentUsername);
            return "error";
        }

        // Handle missing art style
        if (artStyle == null || artStyle.isBlank()) {
            logger.warn("Art style is blank; defaulting to 'Virtuosic baroque fantasy Painting'");
            artStyle = "Virtuosic baroque fantasy Painting";
        }

        // Convert mana array to a Set for easier lookup
        Set<String> manaSet = new HashSet<>(Arrays.asList(mana));

        // Generate unique IDs for deck and deck idea
        UUID deckIdeaId = UUID.randomUUID();
        UUID deckId = UUID.randomUUID();

        // Create and save the DeckIdea
        DeckIdea deckIdea = new DeckIdea(
                theme, 
                legend, 
                manaSet.contains("red"), 
                manaSet.contains("green"), 
                manaSet.contains("black"), 
                manaSet.contains("blue"), 
                manaSet.contains("white"), 
                deckId.toString(), 
                deckIdeaId.toString(), 
                vibe, 
                artStyle
        );
        ideaDao.save(deckIdea);
        logger.info("DeckIdea saved: {}", deckIdea);

        // Generate the deck and save its data
        Deck deck = cardNamesGenerator.generateCardNames(theme, deckIdea);
        deck.setDeckId(deckId.toString());
        deck.setUser_id(currentUser.getUserId());
        deck.setName(name);
        deckDao.save(deck);

        // Save the cards associated with the deck
        cardDao.saveAll(deck.getCards(), deckId);
        logger.info("Deck and associated cards saved for user: {}", currentUsername);

        // Update deck status to "in progress"
        deckDao.updateStatusInProgress(deckId.toString());

        // Add attributes to the model for the view
        model.addAttribute("cardList", deck.getCards());
        model.addAttribute("deck", deck);

        return "deck-list";
    }
}
