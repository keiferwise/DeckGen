package com.kif.deckgen.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kif.deckgen.services.DeckService;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.Deck;
import com.kif.deckgenmodels.DeckIdea;
import com.kif.deckgenmodels.User;
import com.kif.deckgenmodels.daos.CardDao;
import com.kif.deckgenmodels.daos.DeckDao;
import com.kif.deckgenmodels.daos.DeckIdeaDao;
import com.kif.deckgenmodels.daos.MinioDao;
import com.kif.deckgenmodels.daos.UserDao;

@Controller
public class DecksController {

    private static final Logger logger = LoggerFactory.getLogger(DecksController.class);

    @Autowired
    private CardDao cardDao;
    @Autowired
    private DeckDao deckDao;
    @Autowired
    private DeckIdeaDao ideaDao;
    @Autowired 
    private MinioDao minio;
    @Autowired
    private DeckService deckService;
    @Autowired
    private UserDao userDao;

    /**
     * Handles deck submission and generation.
     */
    @PostMapping("/submit-deck")
    public String generateDeck(@RequestParam("deckId") String currentDeckId, Model model) {
        logger.info("Generating deck for deckId: {}", currentDeckId);

        // Fetch deck and associated cards
        List<Card> cards = cardDao.findAllByDeckId(currentDeckId);
        Deck deck = deckDao.findDeckById(currentDeckId);
        deck.setCards(cards);

        // Fetch deck idea and initiate deck generation via service
        DeckIdea idea = ideaDao.findByDeckId(currentDeckId);
        deckService.createDeck(idea.getDeckIdeaId(), currentDeckId).subscribe();

        // Add attributes to the model for the view
        model.addAttribute("deckId", currentDeckId);
        return decks(model); // Redirect to the decks listing page
    }

    /**
     * Cancels deck submission and deletes the deck.
     */
    @PostMapping("/cancel-submit-deck")
    public String cancelDeck(@RequestParam("deckId") String currentDeckId, Model model) {
        logger.info("Cancelling deck submission for deckId: {}", currentDeckId);

        // Delete associated cards and deck
        cardDao.deleteCardsByDeckId(currentDeckId);
        deckDao.deleteDeckOnlyById(currentDeckId);

        return "/deck-gen"; // Return to the deck generation page
    }

    /**
     * Lists all decks for the current user.
     */
    @GetMapping("/decks")
    public String decks(Model model) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Fetching decks for user: {}", currentUser.getName());

        User user = userDao.findUserByName(currentUser.getName());
        List<Deck> myDecks = deckDao.findDecksByUserId(user.getUserId());

        logger.debug("User {} has {} decks", user.getUserId(), myDecks.size());
        model.addAttribute("myDecks", myDecks);

        return "decks"; // Return the view for listing decks
    }

    /**
     * Shows the details of a single deck and its cards.
     */
    @GetMapping("/deck/{deckId}")
    public String deck(@PathVariable String deckId, Model model) {
        logger.info("Displaying deck details for deckId: {}", deckId);

        List<Card> cards = cardDao.findAllByDeckId(deckId);
        Deck deck = deckDao.findDeckById(deckId);
        model.addAttribute("cards", cards);
        model.addAttribute("deck", deck);

        return "deck"; // Return the view to display deck details
    }

    /**
     * Shows all cards in the deck as images.
     */
    @GetMapping("/deck/allcards/{deckId}")
    public String deckCardGrid(@PathVariable String deckId, Model model) {
        logger.info("Displaying card grid for deckId: {}", deckId);

        List<Card> cards = deckDao.findDeckById(deckId).getCards();
        ArrayList<HashMap<String, String[]>> imageList = new ArrayList<>();
        ArrayList<String> images = new ArrayList<>();

        for (Card card : cards) {
            HashMap<String, String[]> cardMap = new HashMap<>();
            cardMap.put("rules", card.getRulesForTemplate("mid").split("<NEWLINE>"));
            cardMap.put("flavor", card.getFlavorText().split("<NEWLINE>"));
            cardMap.put("image", new String[] { minio.getImage(card.getCardId()) });
            imageList.add(cardMap);
            images.add(minio.getImage(card.getCardId()));
        }

        model.addAttribute("images", images);
        model.addAttribute("imageList", imageList);

        return "card-grid"; // Return the view for card grid display
    }

    /**
     * Displays a printable version of the cards in the deck.
     */
    @GetMapping("/deck/printable-nine/{deckId}")
    public String deckCardPrintableNine(@PathVariable String deckId, Model model) {
        logger.info("Displaying printable version of deckId: {}", deckId);

        List<Card> cards = deckDao.findDeckById(deckId).getCards();
        ArrayList<HashMap<String, String[]>> imageList = new ArrayList<>();
        ArrayList<String> images = new ArrayList<>();

        for (Card card : cards) {
            HashMap<String, String[]> cardMap = new HashMap<>();
            cardMap.put("rules", card.getRulesForTemplate("mid").split("<NEWLINE>"));
            cardMap.put("flavor", card.getFlavorText().split("<NEWLINE>"));
            cardMap.put("image", new String[] { minio.getImage(card.getCardId()) });
            imageList.add(cardMap);
            images.add(minio.getImage(card.getCardId()));
        }

        model.addAttribute("images", images);
        model.addAttribute("imageList", imageList);

        return "printable-nine"; // Return the view for printable nine cards
    }
}
