package com.kif.cardgen.services;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kif.deckgenmodels.Card;

@Service
public class CardComposer {
    private String framePath;
    private String artPath;
    private String manaPath = "D:\\deckgen\\deck-gen-main\\src\\main\\resources\\images\\mana-symbols\\";
    private static final Logger logger = LoggerFactory.getLogger(CardComposer.class);

    public CardComposer(String framePath, String artPath) {
        this.framePath = framePath;
        this.artPath = artPath;
    }

    public CardComposer() {
    }

    public BufferedImage createImage(Card card, BufferedImage cardArt) throws IOException {
        logger.info("Starting to create card image for card: {}", card.getName());
        
        int width = 1200;
        int height = 1680;

        card.setArtist("Dall e");
        card.setCopyright("Wizards of the Coast 2023");

        logger.debug("Registering fonts");
        registerFont("D:\\deckgen\\deck-gen-main\\src\\main\\resources\\fonts\\EBGaramond-Medium.ttf");
        registerFont("D:\\deckgen\\deck-gen-main\\src\\main\\resources\\fonts\\GoudyMediaevalRegular.ttf");
        registerFont("D:\\deckgen\\deck-gen-main\\src\\main\\resources\\fonts\\EBGaramond-Italic.ttf");
        registerFont("D:\\deckgen\\deck-gen-main\\src\\main\\resources\\fonts\\EBGaramond-SemiBoldItalic.ttf");

        logger.debug("Setting up fonts and paths");
        Font rulesFont = new Font("EB Garamond Medium", Font.PLAIN, 50);
        Font nameFont = new Font("Goudy Mediaeval", Font.PLAIN, 65);

        framePath = getFrame(card);
        logger.info("Frame path resolved to: {}", framePath);

        BufferedImage frameImage = ImageIO.read(new File(framePath));
        BufferedImage artImage = cardArt;

        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = combinedImage.createGraphics();
        g2d.drawImage(artImage, 90, 160, null);
        g2d.drawImage(frameImage, 0, 0, null);

        logger.debug("Combining frame and art images");

        card.setManaCost(card.getManaCost().replaceAll("[^WUBRG1234567890{}]", ""));
        logger.info("Card mana cost sanitized: {}", card.getManaCost());

        int x = width - 180;
        int y = 85;
        int manaSymbolSize = 50;
        int[] manaArray = orderManaCost(card.getManaCost());
        int cmc = 0;
        int colourlessManaAmount = manaArray[0];

        if (!(card.getType().toLowerCase().equals("land") || card.getType().toLowerCase().equals("basic land"))) {
            for (int q = 1; q < 6; q++) {
                for (int r = 0; r < manaArray[q]; r++) {
                    cmc++;
                    String manaSymbolPath = manaPath + getManaSymbolFileName(q - 1);
                    BufferedImage manaImage = ImageIO.read(new File(manaSymbolPath));
                    g2d.drawImage(manaImage, x, y, manaSymbolSize, manaSymbolSize, null);
                    x -= 60;
                }
            }
            if (colourlessManaAmount > 0 || cmc == 0) {
                cmc += colourlessManaAmount;
                String manaSymbolPath = manaPath + colourlessManaAmount + ".png";
                BufferedImage colourlessManaImage = ImageIO.read(new File(manaSymbolPath));
                g2d.drawImage(colourlessManaImage, x, y, manaSymbolSize, manaSymbolSize, null);
            }
        }

        g2d.dispose();
        logger.info("Card image created successfully for card: {}", card.getName());
        return combinedImage;
    }

    private void registerFont(String fontPath) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)));
            logger.debug("Font registered: {}", fontPath);
        } catch (FontFormatException | IOException e) {
            logger.error("Error registering font: {}", fontPath, e);
        }
    }

    private String getFrame(Card card) {
        logger.debug("Determining frame for card type: {}", card.getType());
        String frameColour = "";
        String path = "D:\\deckgen\\cardgen\\src\\main\\resources\\images\\card-frames\\";
        int colourCounter = 0;

        if (card.getManaCost().contains("{W}")) {
            frameColour += "white";
            colourCounter++;
        }
        if (card.getManaCost().contains("{U}")) {
            frameColour += "blue";
            colourCounter++;
        }
        if (card.getManaCost().contains("{B}")) {
            frameColour += "black";
            colourCounter++;
        }
        if (card.getManaCost().contains("{R}")) {
            frameColour += "red";
            colourCounter++;
        }
        if (card.getManaCost().contains("{G}")) {
            frameColour += "green";
            colourCounter++;
        }

        if (colourCounter >= 3) {
            frameColour = "multicolour";
        } else if (colourCounter == 0 && card.getType().toLowerCase().contains("artifact")) {
            frameColour = "artifact";
        } else if (colourCounter == 0) {
            frameColour = "colourless";
        }

        path = path + frameColour + ".png";
        logger.debug("Frame determined: {}", path);
        return path;
    }

    private String getManaSymbolFileName(int index) {
        String[] manaSymbolFileNames = { "w.png", "u.png", "b.png", "r.png", "g.png" };
        return manaSymbolFileNames[index];
    }

    private int[] orderManaCost(String manaCost) {
        int[] manaArray = new int[6];
        for (char c : manaCost.toCharArray()) {
            if (isNumeric(String.valueOf(c))) {
                manaArray[0] = Integer.parseInt(manaCost.replaceAll("[WUBRGwbrg{}]+", ""));
            } else {
                switch (c) {
                    case 'W': manaArray[1]++; break;
                    case 'U': manaArray[2]++; break;
                    case 'B': manaArray[3]++; break;
                    case 'R': manaArray[4]++; break;
                    case 'G': manaArray[5]++; break;
                    default: break;
                }
            }
        }
        logger.debug("Mana cost ordered: {}", manaArray);
        return manaArray;
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}