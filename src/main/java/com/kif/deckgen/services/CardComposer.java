package com.kif.deckgen.services;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class CardComposer {
    private String framePath = "D:\\deckgen\\src\\main\\resources\\images\\simplecard.png";
    private String artPath = "D:\\deckgen\\src\\main\\resources\\images\\1a244e99-bae5-4d4c-bd80-edf03604cd4d.png";
    //private String backgroundPath;

    public CardComposer(String framePath, String artPath) {
        this.framePath = framePath;
        this.artPath = artPath;
    }
    public CardComposer() {

    }
    
    

    public void createImage(String mana, String name, String type, String subtype, String rulesText, String flavorText, String power, String toughness, String copywrite, String artist) throws IOException {
        BufferedImage frameImage = ImageIO.read(new File(framePath));
        BufferedImage artImage = ImageIO.read(new File(artPath));

        int width = 1200;
        int height = 1680;

        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = combinedImage.createGraphics();
        g2d.drawImage(artImage, 90, 160, null);

        g2d.drawImage(frameImage, 0, 0, null);

        Font font = new Font("Arial", Font.BOLD, 30);
        g2d.setFont(font);

        int x = width - 200;
        int y = 50;

        for (int i = 0; i < mana.length(); i += 1) {
            char c = mana.charAt(i);
            Color color;
            switch (c) {
                case 'W':
                    color = Color.WHITE;
                    break;
                case 'U':
                    color = Color.BLUE;
                    break;
                case 'B':
                    color = Color.BLACK;
                    break;
                case 'R':
                    color = Color.RED;
                    break;
                case 'G':
                    color = Color.GREEN;
                    break;
                default:
                    color = Color.GRAY;
            }
            g2d.setColor(color);
            g2d.fillOval(x - (i * 50), y, 40, 40);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x - (i * 50), y, 40, 40);
            g2d.drawString(String.valueOf(c), x - (i * 50) + 15, y + 30);
        }

        font = new Font("Serif", Font.BOLD, 60);
        g2d.setFont(font);

        x = width / 3 - name.length() * 20;
        y += 90;

        g2d.drawString(name, x, y);

        font = new Font("Arial", Font.PLAIN, 40);
        g2d.setFont(font);

        //x -= type.length() * 20 + subtype.length() * 20 + rulesText.length() * 10 + flavorText.length() * 10 + power.length() * 10 + toughness.length() * 10 + copywrite.length() * 5 + artist.length() * 5;

        //y += 100;
        x = 200;
        y = 1200;

        g2d.drawString(type + " - " + subtype, x, y);


        font = new Font("Arial", Font.PLAIN, 30);
      g2d.setFont(font);

        g2d.drawString(rulesText, x, y);

        y += rulesText.split("\n").length * font.getSize();

        font = new Font("Arial", Font.ITALIC | Font.BOLD, 30);
        g2d.setFont(font);

        g2d.drawString(flavorText, x, y);

        font = new Font("Arial", Font.BOLD, 60);
        g2d.setFont(font);

        x += width / 3;

        y -= rulesText.split("\n").length * font.getSize();

        g2d.drawString(power + "/" + toughness, x, y);

        font = new Font("Arial", Font.PLAIN, 20);
        g2d.setFont(font);

        x -= copywrite.length() * font.getSize();

        y += font.getSize() * (rulesText.split("\n").length - flavorText.split("\n").length) / 2;

        g2d.drawString(copywrite + " | Art by " + artist, x, y);

        g2d.dispose();

        ImageIO.write(combinedImage, "png", new File("D:\\out-images\\"+ name + UUID.randomUUID().toString() +".png"));
    }
}
