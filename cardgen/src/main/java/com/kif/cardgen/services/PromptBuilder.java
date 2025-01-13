package com.kif.cardgen.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.DeckIdea;

@Component
public class PromptBuilder {
    private static final Logger logger = LoggerFactory.getLogger(PromptBuilder.class);

    @Value("${com.kif.cardDetailsTemplate}")
    private String cardDetailsTemplate;

    @Value("${com.kif.cardDetailsTemplateForDeck}")
    private String cardDetailsTemplateForDeck;

    @Value("${com.kif.deckListTemplate}")
    private String deckListTemplate;

    private String cRules = "Creatures must have a power, toughness, and a subtype. Rules text is optional but recommended. If there is no rules text, then flavor text is mandatory.";
    private String eRules = "Enchantments must have rules text; they have no power or toughness and don't have a subtype.";
    private String sRules = "Sorceries must have rules text; flavor text is optional. Sorceries have no power or toughness and don't have a subtype.";
    private String iRules = "Instants must have rules text; flavor text is optional. Instants have no power or toughness and don't have a subtype.";
    private String aRules = "Artifacts must have rules text; flavor text is optional. Artifacts have no power or toughness and don't have a subtype.";

    private String twoColourRules = "Since this deck is two-colored, at least half the cards should have both <MANA> mana colors in their cost.";
    private String threeColourRules = "Since this deck is three-colored, roughly one-third of the cards should have all three of the mana colors (<MANA>) in their cost. The others can be one or two mana colors, with at least one colorless artifact.";
    private String multicolourRules = "Since this deck is multicolored, roughly one-third of the cards should have all the mana colors (<MANA>) in their cost. The others can be one, two, or three mana colors, with at least one colorless artifact.";

    public PromptBuilder() {
        logger.debug("PromptBuilder instantiated.");
    }

    public String buildDeckPrompt(String theme, String mana) {
        logger.info("Building deck prompt for theme: {} and mana: {}", theme, mana);
        try {
            String prompt = deckListTemplate.replace("<MYTHEME>", theme).replace("<MANA>", mana);
            logger.debug("Deck prompt built successfully: {}", prompt);
            return prompt;
        } catch (Exception e) {
            logger.error("Error building deck prompt", e);
            throw e;
        }
    }

    public String buildCardPrompt(Card card, DeckIdea deckIdea) {
        logger.info("Building card prompt for card: {} in deck: {}", card.getName(), deckIdea.getDeckIdeaId());
        try {
            String prompt;

            if ("none".equals(deckIdea.getDeckIdeaId())) {
                prompt = cardDetailsTemplateForDeck.replace("<NAME>", card.getName())
                        .replace("<TYPE>", card.getType())
                        .replace("<SUBTYPE>", card.getSubtype())
                        .replace("<THEME>", deckIdea.getTheme())
                        .replace("<MANACOST>", card.getManaCost());
            } else {
                prompt = cardDetailsTemplate.replace("<NAME>", card.getName())
                        .replace("<TYPE>", card.getType())
                        .replace("<THEME>", deckIdea.getTheme())
                        .replace("<MANACOST>", card.getManaCost());
            }

            prompt = addManaRules(prompt, deckIdea);
            String cardType = card.getType().toLowerCase();
            if (cardType.contains("creature")) {
                prompt = prompt.replace("<CARDRULES>", cRules);
            } else if (cardType.contains("enchantment")) {
                prompt = prompt.replace("<CARDRULES>", eRules);
            } else if (cardType.contains("artifact")) {
                prompt = prompt.replace("<CARDRULES>", aRules);
            } else if (cardType.contains("instant")) {
                prompt = prompt.replace("<CARDRULES>", iRules);
            } else if (cardType.contains("sorcery")) {
                prompt = prompt.replace("<CARDRULES>", sRules);
            } else {
                prompt = prompt.replace("<CARDRULES>", "");
                logger.warn("Card type not recognized: {}", card.getType());
            }

            logger.debug("Card prompt built successfully: {}", prompt);
            return prompt;
        } catch (Exception e) {
            logger.error("Error building card prompt", e);
            throw e;
        }
    }

    private String addManaRules(String prompt, DeckIdea idea) {
        logger.debug("Adding mana rules for deck idea: {}", idea.getDeckIdeaId());
        int colourCounter = 0;
        if (idea.isWhite()) colourCounter++;
        if (idea.isBlue()) colourCounter++;
        if (idea.isBlack()) colourCounter++;
        if (idea.isRed()) colourCounter++;
        if (idea.isGreen()) colourCounter++;

        String manaRules;
        if (colourCounter == 0) {
            manaRules = "This deck is colorless, so none of the cards should have any mana cost other than colorless.";
        } else if (colourCounter == 1) {
            manaRules = "";
        } else if (colourCounter == 2) {
            manaRules = twoColourRules;
        } else if (colourCounter == 3) {
            manaRules = threeColourRules;
        } else {
            manaRules = multicolourRules;
        }

        prompt = prompt.replace("<MANARULES>", manaRules);
        logger.debug("Mana rules added: {}", manaRules);
        return prompt;
    }

    private String getDeckColours(DeckIdea idea) {
        logger.debug("Determining deck colors for deck idea: {}", idea.getDeckIdeaId());
        StringBuilder cardColour = new StringBuilder();
        if (idea.isWhite()) cardColour.append("white, ");
        if (idea.isBlue()) cardColour.append("blue, ");
        if (idea.isBlack()) cardColour.append("black, ");
        if (idea.isRed()) cardColour.append("red, ");
        if (idea.isGreen()) cardColour.append("green, ");

        if (cardColour.length() == 0) {
            logger.debug("Deck is colorless.");
            return "Colorless";
        }

        String result = cardColour.substring(0, cardColour.length() - 2);
        logger.debug("Deck colors determined: {}", result);
        return result;
    }
}
