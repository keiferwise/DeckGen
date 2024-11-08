package com.kif.deckgen.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.daos.CardDao;
import com.kif.deckgenmodels.daos.MinioDao;

@Controller
public class BoosterPackController {

    private static final Logger logger = LoggerFactory.getLogger(BoosterPackController.class);

    @Autowired
    private MinioDao minio;

    @Autowired
    private CardDao cardDao;

    // Constructor left for dependency injection by Spring
    public BoosterPackController() {
        // Spring will automatically handle dependencies injection
    }

    @GetMapping("/booster")
    public String booster(Model model) {
        logger.info("Booster page opened.");

        // Capture the current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        logger.info("{} is opening a booster pack", currentPrincipalName);

        // Start time measurement for performance
        long startTime = System.currentTimeMillis();

        try {
            // Fetch 9 random cards
            ArrayList<Card> cards = (ArrayList<Card>) cardDao.nineRandom();
            logger.debug("Fetched {} random cards", cards.size());

            // Prepare images and other data for the model
            HashMap<String, String[]> cardMap;
            ArrayList<HashMap<String, String[]>> imageList = new ArrayList<>();
            ArrayList<String> images = new ArrayList<>();

            for (Card c : cards) {
                cardMap = new HashMap<>();
                
                // Add rules text if present
                if (c.getRulesText() != null) {
                    cardMap.put("rules", c.getRulesForTemplate("small").split("<NEWLINE>"));
                }
                
                // Add flavor text if present
                if (c.getFlavorText() != null) {
                    cardMap.put("flavor", c.getFlavorText().split("<NEWLINE>"));
                }
                
                // Add image associated with the card
                String imageUrl = minio.getImage(c.getCardId());
                cardMap.put("image", new String[] { imageUrl });
                
                // Log image data for the card
                logger.debug("Card with ID {} has image: {}", c.getCardId(), imageUrl);
                
                imageList.add(cardMap);
                images.add(imageUrl);
            }

            // Add attributes to the model
            model.addAttribute("images", images);
            model.addAttribute("imageList", imageList);
            model.addAttribute("cards", cards);

            // Log how many images and cards are being processed
            logger.info("Processed {} images and cards for the booster pack", cards.size());

        } catch (Exception e) {
            // Log any error that occurs during the process
            logger.error("Error occurred while fetching booster pack data for user: {}", currentPrincipalName, e);
            model.addAttribute("error", "An error occurred while processing the booster pack.");
            return "error"; // Return an error view if something goes wrong
        }

        // Measure the total time taken
        long elapsedTime = System.currentTimeMillis() - startTime;
        logger.info("Booster pack processed in {} ms", elapsedTime);

        // Return the view name for the booster page
        return "booster";
    }
}
