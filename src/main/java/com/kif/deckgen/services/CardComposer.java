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
    private String framePath = "D:\\deckgen\\src\\main\\resources\\images\\white.png";
    private String artPath = "D:\\deckgen\\src\\main\\resources\\images\\img-H7MTllJItxyHXRMlnO77hB9I.png";
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
        int newParagraphSize = 75;
        int newlineSize = 40;

        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = combinedImage.createGraphics();
        g2d.drawImage(artImage, 90, 160, null);

        g2d.drawImage(frameImage, 0, 0, null);

        Font font = new Font("Arial", Font.BOLD, 30);
        g2d.setFont(font);

        int x = width - 200;
        int y = 100;

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
        //Card Name
        x = width / 3 - name.length() * 20;
        y += 40;
        g2d.drawString(name, x, y);

        //Type and subtype
        font = new Font("Serif", Font.BOLD, 40);
        g2d.setFont(font);
        x = 180;
        y = 980;
        g2d.drawString(type + " - " + subtype, x, y);

        // Rules text
        font = new Font("Arial", Font.PLAIN, 30);
        x = 200;
        y = 1050;
        g2d.setFont(font);
        
        String[] rules =  rulesText.split("<NEWLINE>");
        
        String currentLine = new String();
        int lineCount=1;
        for(String rule : rules) {
        	
        	while(rule.length() * font.getSize() > 1600) {
        		
        		int breakPosition = 1600/font.getSize();
        		
        		while( !rule.substring(breakPosition,breakPosition+1).equals(" ")) {
        			
        			breakPosition++;
        			
        		}
        		currentLine = rule.substring(0,breakPosition);
        		rule = rule.substring(breakPosition);
        		y+=newlineSize;
            	g2d.drawString(currentLine, x, y);
        		lineCount++;
        	}
        	
    		y+=newlineSize;
        	g2d.drawString(rule, x, y);
    		lineCount++;
        	
        }
        
        //Flavor Text
        y += newParagraphSize;//rulesText.split("\n").length * font.getSize();
        font = new Font("Arial", Font.ITALIC | Font.BOLD, 30);
        g2d.setFont(font);
        g2d.drawString(flavorText, x, y);

        
        font = new Font("Arial", Font.BOLD, 60);
        g2d.setFont(font);

        x = width - width / 7;

        y = height - height / 16;

        g2d.drawString(power + "/" + toughness, x, y);

        font = new Font("Arial", Font.PLAIN, 20);
        g2d.setFont(font);

        x =  width/2 - ((copywrite.length()+artist.length()+10)/2 * font.getSize()/3);

        //y += font.getSize() * (rulesText.split("\n").length - flavorText.split("\n").length) / 2;

        g2d.drawString(copywrite + " | Art by " + artist, x, y);

        g2d.dispose();

        ImageIO.write(combinedImage, "png", new File("D:\\out-images\\"+ name + UUID.randomUUID().toString() +".png"));
    }
}
