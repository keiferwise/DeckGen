package com.kif.cardgen.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.DeckIdea;
import com.kif.deckgenmodels.Image;
import com.kif.deckgenmodels.ImageResult;
import com.kif.deckgenmodels.SingleRequest;
import com.kif.deckgenmodels.services.ChatApiClient;
import com.kif.deckgenmodels.services.ChatGPTClient;
import com.kif.deckgenmodels.services.DalleClient;

import com.kif.deckgenmodels.daos.CardDao;
import com.kif.deckgenmodels.daos.DeckIdeaDao;
import com.kif.deckgenmodels.daos.MinioDao;

/**
 * 
 * @author Keifer
 * 
 *         TODO Class with functions that take card names and generate the cards
 */
@Component
public class CardGenerator {
    private static final Logger logger = LoggerFactory.getLogger(CardGenerator.class);

    @Value("${com.kif.cardDetailsTemplate}")
    private String cardDetailsTemplate;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ChatGPTClient gptClient;
    @Autowired
    ChatApiClient chatApiClient;
    @Autowired
    PromptBuilder pb;
    @Autowired
    CardDao cardDao;
    @Autowired
    DeckIdeaDao ideaDao;
    @Autowired
    CardComposer composer;
    @Autowired
    DalleClient dalle;
    @Autowired
    MinioDao minio;

    public Card createCard(String cardid, String theme, String deckIdeaId) {
        logger.info("Starting card creation for cardId: {}, theme: {}, deckIdeaId: {}", cardid, theme, deckIdeaId);

        Card cardWithAllText = createCardText(cardid, theme, deckIdeaId);
        logger.debug("Generated card details: {}", cardWithAllText);

        try {
            cardDao.updateCard(cardWithAllText, cardWithAllText.getCardId());
            logger.info("Updated card details for cardId: {}", cardid);

            createCardArt(cardWithAllText, ideaDao.findByDeckIdeaId(deckIdeaId).getArtStyle());
            logger.info("Card art creation completed for cardId: {}", cardid);

            cardDao.updateStatusComplete(cardid);
            logger.info("Card status updated to COMPLETE for cardId: {}", cardid);
        } catch (Exception e) {
            cardDao.updateStatusFailed(cardid);
            logger.error("Failed to create card for cardId: {}", cardid, e);
        }

        return cardWithAllText;
    }

    private Card createCardText(String cardid, String theme, String deckIdeaId) {
        logger.info("Generating text for cardId: {}, theme: {}, deckIdeaId: {}", cardid, theme, deckIdeaId);

        Card card = cardDao.getCardById(cardid);
        DeckIdea deckIdea = ideaDao.findByDeckIdeaId(deckIdeaId);

        String prompt = pb.buildCardPrompt(card, deckIdea);
        logger.debug("Generated prompt: {}", prompt);

        Card newCard = sendPromptToGpt(prompt);
        logger.debug("Received card text: {}", newCard);

        newCard.setCardId(card.getCardId());
        newCard.setDeckId(card.getDeckId());
        newCard.setStatus("INPROGRESS");

        return newCard;
    }

    private int createCardArt(Card card, String artStyle) {
        logger.info("Creating art for cardId: {}, artStyle: {}", card.getCardId(), artStyle);

        String makeArt = "true";
        logger.debug("Make art flag: {}", makeArt);

        BufferedImage img = null;

        if ("true".equals(makeArt)) {
            ImageResult ir = dalle.generateImage(card.getArtDescription() + ". In this art style: " + artStyle);
            if (ir == null) {
                cardDao.updateStatusFailed(card.getCardId());
                logger.error("Art generation failed for cardId: {}", card.getCardId());
                return 0;
            }

            Image art = ir.getData().get(0);

            try {
                URL url = new URL(art.getUrl());
                img = ImageIO.read(url);
            } catch (IOException e) {
                cardDao.updateStatusFailed(card.getCardId());
                logger.error("Failed to download or read art image for cardId: {}", card.getCardId(), e);
                return 0;
            }
        }

        try {
            BufferedImage cardImage = composer.createImage(card, img);
            cardDao.updateStatusComplete(card.getCardId());
            return minio.saveImage(cardImage, card.getCardId());
        } catch (IOException e) {
            cardDao.updateStatusFailed(card.getCardId());
            logger.error("Failed to create or save card image for cardId: {}", card.getCardId(), e);
            return 0;
        }
    }

    public Card createSingleCard(SingleRequest sr, String cardId) {
        logger.info("Creating single card with cardId: {}", cardId);

        Card newCard = createSingleCardText(sr);
        newCard.setCardId(cardId);

        createCardArt(newCard, sr.getArtStyle());
        return newCard;
    }

    public Card createSingleCardText(SingleRequest sr) {
        logger.info("Generating text for single card with name: {}", sr.getName());

        Card card = new Card();
        DeckIdea idea = new DeckIdea();

        card.setName(sr.getName());
        card.setManaCost(sr.getMana());
        card.setType(sr.getType());
        card.setSubtype(sr.getSubType());

        idea.setArtStyle(sr.getArtStyle());
        idea.setTheme(sr.getTheme());
        idea.setVibe(sr.getVibe());

        String prompt = pb.buildCardPrompt(card, idea);
        return sendPromptToGpt(prompt);
    }

    private Card sendPromptToGpt(String prompt) {
        logger.debug("Sending prompt to GPT: {}", prompt);

        String newCardJson = gptClient.generateCompletion(prompt, 2000);
        Card newCard = new Card();

        try {
            newCard = objectMapper.readValue(newCardJson, Card.class);
            logger.debug("Successfully parsed GPT response: {}", newCard);
        } catch (JsonProcessingException e) {
            newCard.setStatus("FAILED");
            logger.error("Failed to parse GPT response", e);
        }

        return newCard;
    }
}
