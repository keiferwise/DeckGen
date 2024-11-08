package com.kif.deckgen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kif.deckgen.services.CardService;
import com.kif.deckgen.utilities.ManaCostUtility;
import com.kif.deckgenmodels.services.DalleClient;
import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.Image;
import com.kif.deckgenmodels.daos.CardDao;
import com.kif.deckgenmodels.daos.MinioDao;

/**
 * This Controller is for viewing cards and the card page.
 * @author Keifer
 */
@Controller
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    @Autowired
    CardService cardService;

    @Autowired
    CardDao cardDao;

    @Autowired
    DalleClient dalleClient;

    @Autowired
    MinioDao minio;

    @Autowired
    ManaCostUtility manaCostUtility;

    @Value("${com.kif.site-title}")
    private String siteTitle;

    @GetMapping("/card/{cardId}")
    public String Card(@PathVariable String cardId, Model model) {
        logger.info("Entering Card method with cardId: {}", cardId);

        try {
            Card card = cardDao.getCardById(cardId);
            model.addAttribute(card);

            Image image = new Image();
            image.setUrl(minio.getImage(cardId));
            model.addAttribute("image", image);

            model.addAttribute("flavor", card.getFlavorText() != null ? card.getFlavorText().split("<NEWLINE>") : "");
            model.addAttribute("rules", card.getRulesText() != null ? card.getRulesForTemplate("small").split("<NEWLINE>") : "");
            model.addAttribute("font-size", card.getTextSize("small"));
            model.addAttribute("manaCost", manaCostUtility.manaCostForTemplate("Large", card.getManaCost()));

            logger.info("Successfully prepared model attributes for cardId: {}", cardId);
        } catch (Exception e) {
            logger.error("Error retrieving card data for cardId: {}", cardId, e);
            return "error";
        }

        logger.info("Exiting Card method for cardId: {}", cardId);
        return "card";
    }

    @GetMapping("/card/art/{cardId}")
    public String art(@PathVariable String cardId, Model model) {
        logger.info("Entering art method with cardId: {}", cardId);

        try {
            Image image = new Image();
            image.setUrl(minio.getImage(cardId));
            model.addAttribute("image", image);
            logger.info("Image successfully added to model for cardId: {}", cardId);
        } catch (Exception e) {
            logger.error("Error retrieving card art for cardId: {}", cardId, e);
            return "error";
        }

        logger.info("Exiting art method for cardId: {}", cardId);
        return "art";
    }

    @GetMapping("/card/with-text/{cardId}")
    public String cardWithText(@PathVariable String cardId, Model model) {
        logger.info("Entering cardWithText method with cardId: {}", cardId);

        try {
            Card card = cardDao.getCardById(cardId);

            Image image = new Image();
            image.setUrl(minio.getImage(cardId));
            model.addAttribute("image", image);
            model.addAttribute("flavor", card.getFlavorText() != null ? card.getFlavorText().split("<NEWLINE>") : "");
            model.addAttribute("rules", card.getRulesText() != null ? card.getRulesText().split("<NEWLINE>") : "");
            logger.info("Successfully prepared model attributes with text for cardId: {}", cardId);
        } catch (Exception e) {
            logger.error("Error retrieving card with text for cardId: {}", cardId, e);
            return "error";
        }

        logger.info("Exiting cardWithText method for cardId: {}", cardId);
        return "card-with-text";
    }
}
