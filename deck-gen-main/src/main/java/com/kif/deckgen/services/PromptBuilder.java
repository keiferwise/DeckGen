package com.kif.deckgen.services;

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

    @Value("${com.kif.deckListTemplate}")
    private String deckListTemplate;

    private final String cRules = "Creatures must have a power, toughness, and a subtype. Rules text is optional but recommended. If there is no rules text, flavor text is mandatory.";
    private final String eRules = "Enchantments must have rules text. They have no power or toughness and don't have a subtype.";
    private final String sRules = "Sorceries must have rules text. Flavor text is optional. Sorceries have no power or toughness and don't have a subtype.";
    private final String iRules = "Instants must have rules text. Flavor text is optional. Instants have no power or toughness and don't have a subtype.";
    private final String aRules = "Artifacts must have rules text. Flavor text is optional. Artifacts have no power or toughness and don't have a subtype.";
    private final String twoColourRules = "Since this deck is two-colored, at least half the cards should have both <MANA> mana colors in their cost.";
    private final String threeColourRules = "Since this deck is three-colored, roughly one-third of the cards should have all three mana colors (<MANA>) in their cost. The others can have one or two mana colors, with at least one colorless artifact.";
    private final String multicolourRules = "Since this deck is multicolored, roughly one-third of the cards should have all the mana colors (<MANA>) in their cost. The others can have one, two, or three mana colors, with at least one colorless artifact.";

    public String buildDeckPrompt(String theme, String mana) {
        logger.info("Building deck prompt for theme: {} and mana: {}", theme, mana);
        if (deckListTemplate == null || theme == null || mana == null) {
            logger.error("DeckListTemplate, theme, or mana is null. Cannot build prompt.");
            return "";
        }
        return deckListTemplate.replace("<MYTHEME>", theme).replace("<MANA>", mana);
    }

    public String buildCardPrompt(Card card, DeckIdea deckIdea) {
        logger.info("Building card prompt for card: {} in deck idea: {}", card.getName(), deckIdea.getTheme());
        if (cardDetailsTemplate == null) {
            logger.error("CardDetailsTemplate is null. Cannot build card prompt.");
            return "";
        }

        String prompt = cardDetailsTemplate
                .replace("<NAME>", card.getName())
                .replace("<TYPE>", card.getType())
                .replace("<THEME>", deckIdea.getTheme())
                .replace("<MANACOST>", card.getManaCost());

        prompt = addManaRules(prompt, deckIdea);

        String cardRules = getRulesByType(card.getType());
        if (cardRules.isEmpty()) {
            logger.error("Invalid card type: {}. Skipping card rules replacement.", card.getType());
        }
        prompt = prompt.replace("<CARDRULES>", cardRules);

        return prompt;
    }

    private String addManaRules(String prompt, DeckIdea idea) {
        int colourCount = countDeckColours(idea);
        String manaRules = switch (colourCount) {
            case 0 -> "This deck is colorless, so none of the cards should have any mana cost other than colorless.";
            case 1 -> ""; // No special rules for single-colored decks
            case 2 -> twoColourRules;
            case 3 -> threeColourRules;
            default -> multicolourRules;
        };
        return prompt.replace("<MANARULES>", manaRules);
    }

    private int countDeckColours(DeckIdea idea) {
        int count = 0;
        if (idea.isWhite()) count++;
        if (idea.isBlue()) count++;
        if (idea.isBlack()) count++;
        if (idea.isRed()) count++;
        if (idea.isGreen()) count++;
        return count;
    }

    private String getRulesByType(String type) {
        if (type == null || type.isEmpty()) {
            logger.warn("Card type is null or empty.");
            return "";
        }

        String lowerType = type.toLowerCase();
        return switch (lowerType) {
            case "creature" -> cRules;
            case "enchantment" -> eRules;
            case "artifact" -> aRules;
            case "instant" -> iRules;
            case "sorcery" -> sRules;
            default -> "";
        };
    }

    private String getDeckColours(DeckIdea idea) {
        StringBuilder cardColours = new StringBuilder();
        if (idea.isWhite()) cardColours.append("white, ");
        if (idea.isBlue()) cardColours.append("blue, ");
        if (idea.isBlack()) cardColours.append("black, ");
        if (idea.isRed()) cardColours.append("red, ");
        if (idea.isGreen()) cardColours.append("green, ");

        if (cardColours.length() == 0) {
            return "colorless";
        }

        // Remove trailing comma and space
        return cardColours.substring(0, cardColours.length() - 2);
    }
}
