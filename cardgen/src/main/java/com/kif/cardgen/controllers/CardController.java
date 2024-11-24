package com.kif.cardgen.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kif.deckgenmodels.daos.CardDao;
import com.kif.cardgen.services.CardGenerator;
import com.kif.deckgenmodels.util.ApiKeyUtil;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.CardRequest;
import com.kif.deckgenmodels.SingleRequest;

@RestController
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    @Autowired
    private CardDao cardDao;

    @Autowired
    private CardGenerator cardGenerator;

    @Value("${com.kif.sharedsecret}")
    private String sharedSecret;

    @Autowired
    private ApiKeyUtil keyUtil;

    /**
     * Endpoint to create a card for a specific deck.
     *
     * @param cr CardRequest containing card details and API key.
     * @return ResponseEntity indicating success or failure.
     */
    @PostMapping(value = "/create-card-for-deck", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCardForDeck(@RequestBody CardRequest cr) {
        logger.info("Received request to create card for deck: {}", cr.getDeckIdeaId());

        // Validate API key
        if (!keyUtil.calculateSHA256Hash(sharedSecret).equals(cr.getKey())) {
            logger.error("Invalid API key for card creation request.");
            return ResponseEntity.badRequest().body("Invalid API key.");
        }

        try {
            // Generate the card
            cardGenerator.createCard(cr.getCardId(), cr.getTheme(), cr.getDeckIdeaId());
            logger.info("Successfully created card for deck: {}", cr.getDeckIdeaId());
            return ResponseEntity.ok("Request received successfully!");
        } catch (Exception e) {
            logger.error("Error while creating card for deck: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Failed to process the request.");
        }
    }

    /**
     * Endpoint to create a single card.
     *
     * @param sr SingleRequest containing single card details and API key.
     * @return ResponseEntity with the ID of the newly created card or error message.
     */
    @PostMapping("/create-card")
    public ResponseEntity<String> createCard(@RequestBody SingleRequest sr) {
        logger.info("Received request to create a single card.");

        // Validate API key
        if (!keyUtil.calculateSHA256Hash(sharedSecret).equals(sr.getKey())) {
            logger.error("Invalid API key for single card creation request.");
            return ResponseEntity.badRequest().body("Invalid API key.");
        }

        try {
            // Generate and save the card
            String newCardId = UUID.randomUUID().toString();
            Card newCard = cardGenerator.createSingleCard(sr, newCardId);
            cardDao.save(newCard, sr.getDeckId(), newCardId);

            logger.info("Successfully created single card with ID: {}", newCardId);
            return ResponseEntity.ok(newCardId);
        } catch (Exception e) {
            logger.error("Error while creating single card: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Failed to create card.");
        }
    }
}
