package com.kif.deckservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kif.deckgenmodels.DeckRequest;
import com.kif.deckservice.services.DeckGenerator;

@RestController
public class DeckController {

    @Autowired
    private DeckGenerator deckGenerator;

    @Value("${com.kif.sharedsecret}")
    private String key;

    private static final Logger logger = LoggerFactory.getLogger(DeckController.class);

    public DeckController() {
        // Constructor left for dependency injection by Spring
    }

    @PostMapping(value = "/create-deck")
    public ResponseEntity<String> createDeck(@RequestBody DeckRequest dr) {
        // Log the incoming request details at the INFO level
        logger.info("Received request to create deck with deck_id: {} and deck_idea_id: {}", dr.getDeckId(), dr.getDeckIdeaId());
        
        try {
            // Log the key usage (without printing the key itself, for security)
            logger.debug("Using shared secret key for deck generation");
            
            // Call the service to generate the deck
            deckGenerator.makeDeck(dr.getDeckId(), dr.getDeckIdeaId(), key);

            // Log successful deck creation
            logger.info("Successfully created deck with deck_id: {}", dr.getDeckId());
            
            // Return success response
            return ResponseEntity.ok("Request received successfully!");
        } catch (Exception e) {
            // Log any errors that occur during deck creation at the ERROR level
            logger.error("Error occurred while creating deck with deck_id: {}", dr.getDeckId(), e);
            return ResponseEntity.status(500).body("Failed to create deck due to an internal error.");
        }
    }
}
