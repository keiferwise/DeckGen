package com.kif.deckgen.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.Deck;
import com.kif.deckgenmodels.DeckIdea;
import com.kif.deckgenmodels.services.ChatGPTClient;

/**
 * Service to generate card names based on a deck idea and input text.
 * TODO This should take a Deck as a parameter, create a deck-building job 
 * that executes in a different thread and loads the database with cards one by one.
 * 
 * @author Keifer
 */
@Service
public class CardNamesGenerator {
    private static final Logger logger = LoggerFactory.getLogger(CardNamesGenerator.class);

    @Value("${com.kif.deckListTemplate}")
    private String promptTemplate;

    @Autowired
    private ChatGPTClient gptClient;

    @Autowired
    private ObjectMapper objectMapper;

    public Deck generateCardNames(String inputText, DeckIdea deckIdea) {
        logger.info("Starting card name generation for inputText: {} and deckIdea: {}", inputText, deckIdea);

        String mana = manaColour(deckIdea);
        String prompt = promptTemplate.replace("<MYTHEME>", inputText).replace("<MANA>", mana);
        logger.debug("Generated prompt for ChatGPT: {}", prompt);

        String deckJson;
        try {
            deckJson = gptClient.generateCompletion(prompt, 1500);
            logger.debug("Received deck JSON from ChatGPT: {}", deckJson);

            // Extract the relevant JSON portion
            deckJson = deckJson.substring(deckJson.indexOf("["));
            deckJson = deckJson.substring(0, deckJson.length() - 1);
            logger.debug("Extracted JSON array: {}", deckJson);
        } catch (Exception e) {
            logger.error("Error generating card names from ChatGPT for inputText: {}", inputText, e);
            return new Deck(); // Return an empty Deck in case of failure
        }

        Deck deckObject = new Deck();
        try {
            List<Card> cards = objectMapper.readValue(deckJson, new TypeReference<List<Card>>() {});
            logger.info("Successfully deserialized cards: {}", cards);

            if (cards != null) {
                deckObject.setCards(cards);
            }
        } catch (JsonProcessingException e) {
            logger.error("Error parsing deck JSON: {}", deckJson, e);
        }

        logger.info("Completed card name generation for inputText: {}", inputText);
        return deckObject;
    }

    private String manaColour(DeckIdea idea) {
        logger.debug("Determining mana color for DeckIdea: {}", idea);

        StringBuilder result = new StringBuilder();
        if (idea.isBlack()) result.append("Black, ");
        if (idea.isRed()) result.append("Red, ");
        if (idea.isGreen()) result.append("Green, ");
        if (idea.isWhite()) result.append("White, ");
        if (idea.isBlue()) result.append("Blue, ");

        if (result.length() > 2) {
            result.setLength(result.length() - 2); // Remove trailing comma and space
        }

        String manaColors = result.toString();
        logger.debug("Generated mana colors: {}", manaColors);

        return manaColors;
    }
}
