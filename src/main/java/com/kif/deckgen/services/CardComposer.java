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
import java.util.ArrayList;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kif.deckgen.models.Card;

@Service
public class CardComposer {
	private String framePath;
	private String artPath = "D:\\deckgen\\src\\main\\resources\\images\\img-H7MTllJItxyHXRMlnO77hB9I.png";
	//@Value("${com.kif.mana-path}")
	private String manaPath = "D:\\deckgen\\src\\main\\resources\\images\\mana-symbols\\";



	public CardComposer(String framePath, String artPath) {
		this.framePath = framePath;
		this.artPath = artPath;
	}
	public CardComposer() {

	}
/**
 * 
 * @param card
 * @param cardArt
 * @return
 * @throws IOException
 */
	public BufferedImage createImage(Card card, BufferedImage cardArt) throws IOException {
		
		int width = 1200;
		int height = 1680;
		int newParagraphSize = 75;
		int newlineSize = 40;
		int textWidth = 1600;
		card.setArtist("Keifer Wiseman");
		card.setCopyright("Wizards of the Coast 2023");
		Font GaramondMedium = getCustomFont("D:\\deckgen\\src\\main\\java\\EBGaramond-Medium.ttf",50,Font.PLAIN, "EB Garamond Medium");
		Font nameFont = getCustomFont("D:\\deckgen\\src\\main\\java\\GoudyMediaevalRegular.ttf",65,Font.PLAIN, "Goudy Mediaeval");
		framePath = getFrame(card);
		System.out.println(framePath);
		BufferedImage frameImage = ImageIO.read(new File(framePath));
		BufferedImage artImage = ImageIO.read(new File(artPath));
		String[] manaSymbolFileNames = {"w.png","u.png","b.png","r.png","g.png"}; 

		BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = combinedImage.createGraphics();
		g2d.drawImage(artImage, 90, 160, null);

		g2d.drawImage(frameImage, 0, 0, null);
		card.setManaCost(card.getManaCost().replaceAll("[^WUBRG1234567890]", ""));
		Font font = new Font("Arial", Font.BOLD, 30);
		g2d.setFont(font);

		int x = width - 180;
		int y = 85;
		int manaSymbolSize = 50;
		int[] manaArray = orderManaCost( card.getManaCost());
		int cmc = 0;
		int colourlessManaAmount = manaArray[0];
		String colourlessManaFileName = manaArray[0] + ".png";
		String manaSymbolPath="";
		for(int q = 1; q<6; q++) {
			for(int r = 0;r<manaArray[q];r++) {
				cmc++;
				manaSymbolPath = manaPath + manaSymbolFileNames[q-1];
				System.out.println("Mana Symbol Path: "+manaSymbolPath);
				BufferedImage manaImage = ImageIO.read(new File(manaSymbolPath));
				g2d.drawImage(manaImage, x, y,manaSymbolSize,manaSymbolSize, null);
				x-=60;
			}
		}
		if (colourlessManaAmount>0 || cmc==0) {
			cmc+=colourlessManaAmount;
			manaSymbolPath = manaPath + colourlessManaFileName;
			BufferedImage colourlessManaImage = ImageIO.read(new File(manaSymbolPath));
			g2d.drawImage(colourlessManaImage, x, y,manaSymbolSize,manaSymbolSize, null);

		}
		
		/*
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

*/
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		font = new Font("Serif", Font.BOLD, 60);
		g2d.setFont(font);
		//Card Name
		x = width/10;
		y = 130;
		
		drawTextWithOutline( g2d, card.getName(), x,  y, nameFont);

		//Type and subtype
		font = new Font("Serif", Font.BOLD, 40);
		g2d.setFont(GaramondMedium);
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

		drawTextWithOutline( g2d, (typeText), x, y,GaramondMedium);
		// Rules text
		font = new Font("Serif", Font.BOLD, 40);
		x = 200;
		y = 1100;
		g2d.setFont(font);

		String[] rules =  card.getRulesText().split("<NEWLINE>");

		String[] flavors =  card.getFlavorText().split("<NEWLINE>");


		String currentLine = new String();
		int lineCount = 1;
		int maxChars = textWidth/font.getSize()-10;
		System.out.println("there are a total of "+ (numberOfLines(rules,maxChars) +numberOfLines(flavors,maxChars))+" lines");


		g2d.setColor(Color.BLACK);

		for(String rule : rules) {
			//System.out.println(rule);
			ArrayList<String> ruleLines = divideLines(rule,maxChars);

			for(String ruleLine : ruleLines) {
				System.out.println(ruleLine);
				g2d.drawString(ruleLine.trim(), x, y);
				y+=newlineSize;

			}
			y+=newParagraphSize - newlineSize;

		}

		//Flavor Text

		for(String flavor : flavors) {

			if(flavor.isBlank()){continue;}


			//System.out.println(flavor);
			ArrayList<String> flavorLines = divideLines(flavor,maxChars);

			for(String line : flavorLines) {
				System.out.println(line);
				g2d.drawString(line.trim(), x, y);
				y+=newlineSize;

			}
			y += newParagraphSize - newlineSize;

		}

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
		//String file = "";
		int colourCounter=0;
		//Get Colour Identity String
		if(card.getManaCost().contains("W")) {frameColour+="white"; colourCounter++;}
		if(card.getManaCost().contains("U")) {frameColour+="blue"; colourCounter++;}
		if(card.getManaCost().contains("B")) {frameColour+="black"; colourCounter++;}
		if(card.getManaCost().contains("R")) {frameColour+="red"; colourCounter++;}
		if(card.getManaCost().contains("G")) {frameColour+="green"; colourCounter++;}


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
		Font myFont = new  Font(fontName, style, size);
		return myFont; 

	}
	private ArrayList<String> divideLines(String text,int charLimit) {

		String[] splitText =  text.split(" ");
		ArrayList<String> lines = new ArrayList<String>();
		int length=0;
		int wordCount=0;
		String temp = "";
		for(String word : splitText) {
			wordCount++;
			//System.out.println(word);
			length+=word.length();
			temp = temp + " " + word;
			if(length>=charLimit || splitText.length==wordCount ) {
				lines.add(temp);
				//System.out.println(temp+ ", " +length);
				length=0;
				temp="";
			} 
		}

		return lines;
	}
	private int numberOfLines(String[] textList,int charLimit) {

		int length=0;
		int wordCount=0;
		int lineCount = 0;
		for (String text : textList) {
			String[] splitText =  text.split(" ");
			//ArrayList<String> lines = new ArrayList<String>();

			//String temp = "";
			for(String word : splitText) {
				wordCount++;
				length+=word.length();
				//temp = temp + " " + word;
				if(length>=charLimit || splitText.length==wordCount ) {
					//lines.add(temp);
					lineCount++;
					length=0;
					//temp="";
				} 
			}

		}
		return lineCount+(textList.length/2);

	}
	private int [] orderManaCost(String manaCost) {
		int [] manaArray = new int[6];
		for(char c : manaCost.toCharArray()) {
			if(isNumeric(String.valueOf(c))) {
				
				
				manaArray[0] = Integer.parseInt(manaCost.replaceAll("[WUBRGwbrg]+", ""));
			}
			else {
				switch (c) {
				case 'W':
					manaArray[1]+=1;
					break;
				case 'U':
					manaArray[2]+=1;
					break;
				case 'B':
					manaArray[3]+=1;
					break;
				case 'R':
					manaArray[4]+=1;
					break;
				case 'G':
					manaArray[5]+=1;
					break;
				default:
					break;
				}
			}	
		}
		
		String newManaCost = "";
	
	return manaArray;
	}
	private static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
}