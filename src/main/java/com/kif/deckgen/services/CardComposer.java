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
    private String framePath = "D:\\deckgen\\src\\main\\resources\\images\\";
    private String artPath = "D:\\deckgen\\src\\main\\resources\\images\\img-H7MTllJItxyHXRMlnO77hB9I.png";
    //private String backgroundPath;

    public CardComposer(String framePath, String artPath) {
        this.framePath = framePath;
        this.artPath = artPath;
    }
    public CardComposer() {

    }
    
    

    public void createImage(String mana, String name, String type, String subtype, String rulesText, String flavorText, String power, String toughness, String copywrite, String artist) throws IOException {


        
        int width = 1200;
        int height = 1680;
        int newParagraphSize = 75;
        int newlineSize = 40;
        int textWidth = 1600;
        
        String frameColour = "frame";
        
        if(mana.contains("W")) {frameColour+="white";}
        if(mana.contains("U")) {frameColour+="blue";}
        if(mana.contains("B")) {frameColour+="black";}
        if(mana.contains("R")) {frameColour+="red";}
        if(mana.contains("G")) {frameColour+="green";}
        frameColour += ".png";
        
        /* Test */
        frameColour = "white_1.png";
        
        framePath += frameColour;
        
        BufferedImage frameImage = ImageIO.read(new File(framePath));
        BufferedImage artImage = ImageIO.read(new File(artPath));
        
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
        x = width/10;
        y += 40;
        g2d.drawString(name, x, y);

        //Type and subtype
        font = new Font("Serif", Font.BOLD, 40);
        g2d.setFont(font);
        x = 180;
        y = 980;
        g2d.drawString(type + " - " + subtype, x, y);

        // Rules text
        font = new Font("Serif", Font.BOLD, 40);
        x = 200;
        y = 1050;
        g2d.setFont(font);
        
        String[] rules =  rulesText.split("<NEWLINE>");
        
        String currentLine = new String();
        int lineCount=1;
        
        for(String rule : rules) {
        	while(rule.length() * font.getSize() > textWidth) {
        		int breakPosition = textWidth/font.getSize();
        		
        		System.out.println(breakPosition);
        		System.out.println(rule.charAt(breakPosition));

        		
        		while( !rule.substring( breakPosition,breakPosition+1).equals(" ") && !rule.substring(breakPosition,breakPosition+1).equals(".") && !(rule.length()-1==breakPosition)){
            		System.out.println(rule.charAt(breakPosition));

        			breakPosition++;	
        		}
        		currentLine = rule.substring(0,breakPosition);
        		rule = rule.substring(breakPosition);
        		y+=newlineSize;
            	g2d.drawString(currentLine.trim(), x, y);
        		lineCount++;
        	}
    		y+=newlineSize;
        	g2d.drawString(rule.trim(), x, y);
    		lineCount++;
        }
        
        //Flavor Text
        
        String[] flavor =  flavorText.split("<NEWLINE>");
        for(String line : flavor) {
        	if(line.isBlank()){continue;}
        	 y += newParagraphSize;//rulesText.split("\n").length * font.getSize();
             font = new Font("Arial", Font.ITALIC | Font.BOLD, 30);
             g2d.setFont(font);
            
             	while(line.length() * font.getSize() > textWidth) {
             		int breakPosition = textWidth/font.getSize();
             		while( !line.substring( breakPosition,breakPosition+1).equals(" ")  && !(line.length()-1==breakPosition)) {
                		System.out.println(line.charAt(breakPosition));

             			breakPosition++;	
             		}
             		currentLine = line.substring(0,breakPosition);
             		line = line.substring(breakPosition);
             		y+=newlineSize;
                 	g2d.drawString(currentLine.trim(), x, y);
             		lineCount++;
             	}
         		y+=newlineSize;
             	g2d.drawString(line.trim(), x, y);
        }
       

        

        //g2d.drawString(flavorText, x, y);

        
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
