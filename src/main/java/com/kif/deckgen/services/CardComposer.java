package com.kif.deckgen.services;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.kif.deckgen.models.Card;

@Service
public class CardComposer {
	private String framePath;
	private String artPath = "D:\\deckgen\\src\\main\\resources\\images\\img-H7MTllJItxyHXRMlnO77hB9I.png";
	//private String backgroundPath;

	public CardComposer(String framePath, String artPath) {
		this.framePath = framePath;
		this.artPath = artPath;
	}
	public CardComposer() {

	}



	public BufferedImage createImage(Card card, BufferedImage cardArt) throws IOException {
		



		int width = 1200;
		int height = 1680;
		int newParagraphSize = 75;
		int newlineSize = 40;
		int textWidth = 1600;
		card.setArtist("Keifer Wiseman");
		card.setCopyright("Wizards of the Coast 2023");

		framePath = getFrame(card);
		System.out.println(framePath);
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

		for (int i = 0; i < card.getManaCost().length(); i += 1) {
			char c = card.getManaCost().charAt(i);
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
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		font = new Font("Serif", Font.BOLD, 60);
		g2d.setFont(font);
		//Card Name
		x = width/10;
		y += 30;
		drawTextWithOutline( g2d, card.getName(), x,  y, getCustomFont("D:\\deckgen\\src\\main\\java\\GoudyMediaevalRegular.ttf",60,Font.PLAIN, "Goudy Mediaeval"));

		//Type and subtype
		font = new Font("Serif", Font.BOLD, 40);
		g2d.setFont(font);
		x = 180;
		y = 980;
		//g2d.drawString(type + " - " + subtype, x, y);
		String typeText="";
		if(!(card.getSubtype()==null)) {
			if(card.getSubtype().isBlank()) {
				typeText = card.getType();
			}
			else {
				typeText = card.getType() + " - " + card.getSubtype();
			}
		}
		else {
			typeText = card.getType();
		}

		drawTextWithOutline( g2d, (typeText), x, y,font);
		// Rules text
		font = new Font("Serif", Font.BOLD, 40);
		x = 200;
		y = 1050;
		g2d.setFont(font);

		String[] rules =  card.getRulesText().split("<NEWLINE>");

		String currentLine = new String();
		int lineCount=1;

		g2d.setColor(Color.BLACK);

		for(String rule : rules) {
			while(rule.length() * font.getSize() > textWidth) {
				int breakPosition = textWidth/font.getSize();

				//System.out.println(breakPosition);
				//System.out.println(rule.charAt(breakPosition));


				while( !rule.substring( breakPosition,breakPosition+1).equals(" ") && !rule.substring(breakPosition,breakPosition+1).equals(".") && !(rule.length()-1==breakPosition)){
					//System.out.println(rule.charAt(breakPosition));

					breakPosition++;	
				}
				currentLine = rule.substring(0,breakPosition);
				rule = rule.substring(breakPosition);
				y+=newlineSize;
				g2d.drawString(currentLine.trim(), x, y);
				//drawTextWithOutline( g2d, currentLine.trim(), x,  y,font);
				lineCount++;
			}
			y+=newlineSize;
			g2d.drawString(rule.trim(), x, y);
			//drawTextWithOutline( g2d, rule.trim(), x,  y,font);
			lineCount++;
		}

		//Flavor Text

		String[] flavor =  card.getFlavorText().split("<NEWLINE>");
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
				//drawTextWithOutline( g2d, line.trim(), x,  y,font);


				lineCount++;
			}
			y+=newlineSize;
			g2d.drawString(line.trim(), x, y);
			//drawTextWithOutline( g2d, line.trim(), x,  y,font);

		}




		//g2d.drawString(flavorText, x, y);


		font = new Font("Arial", Font.BOLD, 60);
		g2d.setFont(font);

		x = width - width / 7;

		y = height - height / 16;

		//g2d.drawString(power + "/" + toughness, x, y);
		drawTextWithOutline( g2d, (card.getPower() + "/" + card.getToughness()), x,  y,font);


		font = new Font("Arial", Font.PLAIN, 20);
		g2d.setFont(font);

		x =  width/2 - ((card.getCopyright().length()+card.getArtist().length()+10)/2 * font.getSize()/3);

		//y += font.getSize() * (rulesText.split("\n").length - flavorText.split("\n").length) / 2;

		//g2d.drawString(copywrite + " | Art by " + artist, x, y);
		drawTextWithOutline( g2d, (card.getCopyright() + " | Art by " + card.getArtist()), x,  y,font);
		g2d.dispose();

		//This writes the file to d:\out-images for testing
		//ImageIO.write(combinedImage, "png", new File("D:\\out-images\\"+ card.getName() + UUID.randomUUID().toString() +".png"));

		return combinedImage;

	}

	private void drawTextWithOutline(Graphics2D g2d, String text, int x, int y, Font font) {
		//Font font = new Font("Arial", Font.BOLD, 20);
		FontRenderContext frc = g2d.getFontRenderContext();
		GlyphVector gv = font.createGlyphVector(frc, text);
		Shape glyph = gv.getOutline();
		AffineTransform orig = g2d.getTransform();
		g2d.translate(x, y);
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(3));
		g2d.draw(glyph);
		g2d.setColor(Color.white);
		g2d.fill(glyph);
		g2d.setTransform(orig);
	}
	private String getFrame(Card card) {
		String frameColour="";
		String path = "D:\\deckgen\\src\\main\\resources\\images\\";
		String file = "";
		int colourCounter=0;
		//Get Colour Identity String
		if(card.getManaCost().contains("W")) {frameColour+="white"; colourCounter++;}
		if(card.getManaCost().contains("U")) {frameColour+="blue"; colourCounter++;}
		if(card.getManaCost().contains("B")) {frameColour+="black"; colourCounter++;}
		if(card.getManaCost().contains("R")) {frameColour+="red"; colourCounter++;}
		if(card.getManaCost().contains("G")) {frameColour+="green"; colourCounter++;}

		System.out.println(frameColour);

		if(colourCounter>1) {
			frameColour = "Multicolour";
		}
		else if(colourCounter == 0) {
			frameColour="Colourless";
		}

		path = path + frameColour + ".png";

		System.out.println(path);
		return path;
	}
	
	private Font getCustomFont(String fontPath,int size,int style,String fontName) {
		
		//Font goudyMedieval = new Font("Serif", Font.BOLD, 40);;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*for(String s : ge.getAvailableFontFamilyNames()) {
			System.out.println(s);

		}*/
		Font goudyMedieval = new  Font(fontName, style, size);
		System.out.println(goudyMedieval.getFontName() + " " + goudyMedieval.getFamily());
	return goudyMedieval; 
	
	}
}