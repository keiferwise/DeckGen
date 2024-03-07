//package com.kif.deckgen.services;
//
//import java.awt.BasicStroke;
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.FontFormatException;
//import java.awt.FontMetrics;
//import java.awt.Graphics2D;
//import java.awt.GraphicsEnvironment;
//import java.awt.RenderingHints;
//import java.awt.Shape;
//import java.awt.font.FontRenderContext;
//import java.awt.font.GlyphVector;
//import java.awt.geom.AffineTransform;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import javax.imageio.ImageIO;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.kif.deckgenmodels.Card;
//
//@Service
//public class CardComposer {
//	private String framePath;
//	private String artPath ;//= "D:\\deckgen\\src\\main\\resources\\images\\img-H7MTllJItxyHXRMlnO77hB9I.png";
//	//@Value("${com.kif.mana-path}")
//	private String manaPath = "D:\\deckgen\\deck-gen-main\\src\\main\\resources\\images\\mana-symbols\\";
//
//
//
//	public CardComposer(String framePath, String artPath) {
//		this.framePath = framePath;
//		this.artPath = artPath;
//	}
//	public CardComposer() {
//
//	}
///**
// * 
// * @param card
// * @param cardArt
// * @return
// * @throws IOException
// */
//	public BufferedImage createImage(Card card, BufferedImage cardArt) throws IOException {
//		
//		int width = 1200;
//		int height = 1680;
//		int newParagraphSize = 75;
//		int newlineSize = 50;
//		int rulesTextSize=50;
//		int lineLengthMinus = 5;
//		int textWidth = 1700;
//
//		
//		card.setArtist("Dall e");
//		card.setCopyright("Wizards of the Coast 2023");
//
//		registerFont("D:\\deckgen\\deck-gen-main\\src\\main\\resources\\fonts\\EBGaramond-Medium.ttf");
//		registerFont("D:\\deckgen\\deck-gen-main\\src\\main\\resources\\fonts\\GoudyMediaevalRegular.ttf");
//		registerFont("D:\\deckgen\\deck-gen-main\\src\\main\\resources\\fonts\\EBGaramond-Italic.ttf");
//		registerFont("D:\\deckgen\\deck-gen-main\\src\\main\\resources\\fonts\\EBGaramond-SemiBoldItalic.ttf");
//		
//		Font rulesFont = new Font("EB Garamond Medium", Font.PLAIN, rulesTextSize);
//		Font nameFont = new Font("Goudy Mediaeval",Font.PLAIN,65 );
//        
//		
//		String[] manaSymbolFileNames = {"w.png","u.png","b.png","r.png","g.png"}; 
//
//		//Determine what frame to user
//		framePath = getFrame(card);
//
//		//Load art and frame
//		BufferedImage frameImage = ImageIO.read(new File(framePath));
//		//Change this if we are generating images
//		BufferedImage artImage = cardArt;//ImageIO.read(new File(artPath));
//		
//		// Combine the Art and Frame
//		BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g2d = combinedImage.createGraphics();
//		g2d.drawImage(artImage, 90, 160, null);
//		g2d.drawImage(frameImage, 0, 0, null);
//		
//		//Clean the mana cost of other symbols
//		card.setManaCost(card.getManaCost().replaceAll("[^WUBRG1234567890{}]", ""));
//		//Set the initial font
//		//Font font = new Font("Arial", Font.BOLD, 30);
//		//g2d.setFont(font);
//		// init 
//		int x = width - 180;
//		int y = 85;
//		int manaSymbolSize = 50;
//		int[] manaArray = orderManaCost( card.getManaCost());
//		int cmc = 0;
//		int colourlessManaAmount = manaArray[0];
//		String colourlessManaFileName = manaArray[0] + ".png";
//		String manaSymbolPath="";
//		for(int q = 1; q<6; q++) {
//			for(int r = 0;r<manaArray[q];r++) {
//				cmc++;
//				manaSymbolPath = manaPath + manaSymbolFileNames[q-1];
//				//System.out.println("Mana Symbol Path: "+manaSymbolPath);
//				BufferedImage manaImage = ImageIO.read(new File(manaSymbolPath));
//				g2d.drawImage(manaImage, x, y,manaSymbolSize,manaSymbolSize, null);
//				x-=60;
//			}
//		}
//		if (colourlessManaAmount>0 || cmc==0) {
//			cmc+=colourlessManaAmount;
//			manaSymbolPath = manaPath + colourlessManaFileName;
//			BufferedImage colourlessManaImage = ImageIO.read(new File(manaSymbolPath));
//			g2d.drawImage(colourlessManaImage, x, y,manaSymbolSize,manaSymbolSize, null);
//		}// End of mana 
//		
//		
//		//  Define Default Font
//		Font font = new Font("EB Garamond Medium", Font.BOLD, 60);
//		
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		//g2d.setFont(font);
//		//Card Name
//		x = width/10;
//		y = 130;		
//		drawTextWithOutline( g2d, card.getName(), x,  y, nameFont);
//
//		// ## Type and subtype ## //
//		font = new Font("EB Garamond Medium", Font.PLAIN, 55);
//		g2d.setFont(font);
//		x = 180;
//		y = 985;
//		//g2d.drawString(type + " - " + subtype, x, y);
//		String typeText="";
//		if((card.getSubtype()!=null)) {
//			if(card.getSubtype().isBlank() || card.getSubtype().equals("<NONE>")) {
//				typeText = card.getType();
//			}
//			else {
//				typeText = card.getType() + " - " + card.getSubtype();
//			}
//		}
//		else {
//			typeText = card.getType();
//		}
//
//		drawTextWithOutline( g2d, (typeText), x, y,font);
//		
//		/*
//		################
//		## Rules text ##
//		################
//		*/
//		 /*
//		x = 170;
//		g2d.setFont(rulesFont);
//		ArrayList<String> rulesArray = new ArrayList<String>();
//		//String[] rules =  card.getRulesText().split("<NEWLINE>");
//		if(card.getRulesText()!=null) {
//			rulesArray = new ArrayList(Arrays.asList(card.getRulesText().split("<NEWLINE>")));	
//
//		}
//		ArrayList<String> flavorArray =  new ArrayList<String>();
//		//String[] flavors =  card.getFlavorText().split("<NEWLINE>");
//		if(card.getFlavorText()!=null) {
//			 flavorArray = new ArrayList(Arrays.asList(card.getFlavorText().split("<NEWLINE>")));	
//
//		}
//
//		//String currentLine = new String();
//		//int lineCount = 1;
//		
//		rulesArray=removeBlankLines(rulesArray);
//		flavorArray=removeBlankLines(flavorArray);
//		
//		//int maxCharsPerLine = textWidth/rulesFont.getSize()-10;
//		//int maxLines = 11;
//		int maxChars = textWidth/rulesFont.getSize()-lineLengthMinus;
//		int numLines =numberOfLines(rulesArray,maxChars)+numberOfLines(flavorArray,maxChars);
//		System.out.println(numLines);
//
//		int[] textProps = calibrateText( numLines, rulesTextSize,  textWidth,  maxChars);
//		int lineFactor = textProps[4];
//		newParagraphSize = textProps[0];
//		newlineSize= textProps[1];
//		rulesTextSize=textProps[2];
//		//System.out.println(rulesTextSize);
//		maxChars = textProps[3];
//		rulesFont= new Font("EB Garamond Medium", Font.PLAIN, rulesTextSize);
//		Font flavorFont = new Font("EB Garamond SemiBold",Font.ITALIC,rulesTextSize-5);
//		//int NewnumLines =numberOfLines(rulesArray,maxChars)+numberOfLines(flavorArray,maxChars);
// 
//		g2d.setFont(rulesFont);
//
//		y = getTextStartingPoint( numLines,lineFactor);
//		//System.out.println(card.getName()+". There are a total of "+ numLines+" lines. Text Starting at " + y + "px.");
//
//		g2d.setColor(Color.BLACK);
//		
//		for(String rule : rulesArray) {
//			//System.out.println(rule);
//			ArrayList<String> ruleLines = divideLines(rule,maxChars);
//
//			for(String ruleLine : ruleLines) {
//				//System.out.println(ruleLine);
//				
//				
//				
//				g2d.drawString(ruleLine.trim(), x, y);
//				y+=newlineSize;
//
//			}
//			y+=newParagraphSize - newlineSize;
//		}
//		*/
//		/*
//		#################
//		## Flavor text ##
//		#################
//		*/
//		/*
//		y+=newlineSize;
//
//		g2d.setFont(flavorFont);
//
//
//		for(String flavor : flavorArray) {
//
//			if(flavor.isBlank()){continue;}
//
//
//			//System.out.println(flavor);
//			ArrayList<String> flavorLines = divideLines(flavor,maxChars);
//
//			for(String line : flavorLines) {
//				//System.out.println(line);
//				g2d.drawString(line.trim(), x, y);
//				y+=newlineSize;
//
//			}
//			y += newParagraphSize - newlineSize;
//
//		}
//		*/
//		y = height - height / 16;
//
//		if(card.getType().toLowerCase().contains("creature")) {
//			font = new Font("EB Garamond Medium", Font.BOLD, 60);
//			g2d.setFont(font);
//
//			x = width - width / 7;
//
//
//			////g2d.drawString(power + "/" + toughness, x, y);
//			drawTextWithOutline( g2d, (card.getPower() + "/" + card.getToughness()), x,  y,font);
//		}
//
//
//		font = new Font("EB Garamond Medium", Font.PLAIN, 20);
//		g2d.setFont(font);
//		FontMetrics metrics = g2d.getFontMetrics(font);
//		String copyArtist=(card.getCopyright() + " | Art by " + card.getArtist());
//		x =  (width/2) - (metrics.stringWidth(copyArtist)/2);
//		drawTextWithOutline( g2d, copyArtist, x,  y,font);
//		g2d.dispose();
//
//		return combinedImage;
//
//	}
//
//	/** 
//	 * @param g2d
//	 * @param text
//	 * @param x
//	 * @param y
//	 * @param font
//	 */
//	private void drawTextWithOutline(Graphics2D g2d, String text, int x, int y, Font font) {
//		//Font font = new Font("Arial", Font.BOLD, 20);
//		FontRenderContext frc = g2d.getFontRenderContext();
//		GlyphVector gv = font.createGlyphVector(frc, text);
//		Shape glyph = gv.getOutline();
//		AffineTransform orig = g2d.getTransform();
//		g2d.translate(x, y);
//		g2d.setColor(Color.black);
//		g2d.setStroke(new BasicStroke(3));
//		g2d.draw(glyph);
//		g2d.setColor(Color.white);
//		g2d.fill(glyph);
//		g2d.setTransform(orig);
//	}
//	/**
//	 * 
//	 * @param card
//	 * @return
//	 */
//	private String getFrame(Card card) {
//		String frameColour="";
//		String path = "D:\\deckgen\\deck-gen-main\\src\\main\\resources\\images\\card-frames\\";
//		int colourCounter=0;
//		//Get Colour Identity String
//		if(card.getManaCost().contains("{W}")) {frameColour+="white"; colourCounter++;}
//		if(card.getManaCost().contains("{U}")) {frameColour+="blue"; colourCounter++;}
//		if(card.getManaCost().contains("{B}")) {frameColour+="black"; colourCounter++;}
//		if(card.getManaCost().contains("{R}")) {frameColour+="red"; colourCounter++;}
//		if(card.getManaCost().contains("{G}")) {frameColour+="green"; colourCounter++;}
//
//
//		if(colourCounter>=3) {
//			frameColour = "multicolour";
//		}
//		else if(colourCounter == 0  && card.getType().toLowerCase().contains("artifact")){
//			frameColour="artifact";
//		}
//		else if(colourCounter == 0) {
//			frameColour="colourless";
//		}
// 
//		path = path + frameColour + ".png";
//
//		//System.out.println(path);
//		return path;
//	}
//
//
//	
//	private void registerFont(String fontPath) {
//		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		//for (Font f : ge.getAllFonts()) {
//		//	System.out.println(f.getFamily());
//		//}
//		try {
//			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)));
//		} catch (FontFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//
//	/**
//	 * 
//	 * @param text
//	 * @param charLimit
//	 * @return
//	 */
//	private ArrayList<String> divideLines(String text,int charLimit) {
//
//		String[] splitText =  text.split(" ");
//		ArrayList<String> lines = new ArrayList<String>();
//		int length=0;
//		int wordCount=0;
//		String temp = "";
//		for(String word : splitText) {
//			wordCount++;
//			//System.out.println(word);
//			length+=word.length();
//			temp = temp + " " + word;
//			if(length>=charLimit || splitText.length==wordCount ) {
//				lines.add(temp);
//				//System.out.println(temp+ ", " +length);
//				length=0;
//				temp="";
//			} 
//		}
//
//		return lines;
//	}
//	/**
//	 * 
//	 * @param textList
//	 * @param charLimit
//	 * @return
//	 */
//	private int numberOfLines(ArrayList<String> textList,int charLimit) {
//
//		int length=0;
//		int wordCount=0;
//		int lineCount = 0;
//		for (String text : textList) {
//			String[] splitText =  text.split(" ");
//			//ArrayList<String> lines = new ArrayList<String>();
//
//			//String temp = "";
//			for(String word : splitText) {
//				wordCount++;
//				length+=word.length();
//				//temp = temp + " " + word;
//				if(length>=charLimit || splitText.length==wordCount ) {
//					//lines.add(temp);
//					lineCount+=45;
//					length=0;
//					//temp="";
//				} 
//			}
//
//		}
//		return (lineCount+(textList.size()*75))/45;
//
//	}
//	/**
//	 * 
//	 * @param manaCost
//	 * @return
//	 */
//	private int [] orderManaCost(String manaCost) {
//		int [] manaArray = new int[6];
//		for(char c : manaCost.toCharArray()) {
//			if(isNumeric(String.valueOf(c))) {
//				
//				
//				manaArray[0] = Integer.parseInt(manaCost.replaceAll("[WUBRGwbrg{}]+", ""));
//			}
//			else {
//				switch (c) {
//				case 'W':
//					manaArray[1]+=1;
//					break;
//				case 'U':
//					manaArray[2]+=1;
//					break;
//				case 'B':
//					manaArray[3]+=1;
//					break;
//				case 'R':
//					manaArray[4]+=1;
//					break;
//				case 'G':
//					manaArray[5]+=1;
//					break;
//				default:
//					break;
//				}
//			}	
//		}
//			
//	return manaArray;
//	}
//	/**
//	 * @param str
//	 * @return
//	 */
//	private static boolean isNumeric(String str) { 
//		  try {  
//		    Double.parseDouble(str);  
//		    return true;
//		  } catch(NumberFormatException e){  
//		    return false;  
//		  }  
//		}
//	private int getTextStartingPoint(int numLines,int lineFactor) {
//		int y = 1255;
//		
//		int subtract = numLines*lineFactor;
//
//		return y-subtract;
//
//		
//	}
//	private ArrayList<String> removeBlankLines(ArrayList<String> lines) {
//		for(int r=0;r<lines.size();r++) {
//			if(lines.get(r).isBlank()) {
//				lines.remove(r);
//			}
//		}
//		return lines;
//	}
//	
//	private int[] calibrateText(int lines, int font, int textWidth, int maxChars) {
//		int[] props = new int[5];
//		
//		int lineCount = lines;
//		props[0] = (int)font*(5/3);
//		props[1] = font;
//		props[2] = font;
//		props[3] = maxChars;
//		props[4] = 15;
//		double paraAmount= props[0];
//		
//		while(lineCount>=11) {
//			paraAmount=paraAmount-((5/3)*1);
//			props[0]--;
//			props[1]--;
//			props[2]--;
//			props[3]+=2;
//			props[4]--;
//			lineCount--;
//		}
//		props[0]=(int) paraAmount;
//		
//		
//		font = font - (lines);
//		
//		
//		return props;
//	}
//	
//	private int[] calibrateText(int lines) {
//		
//		int[] props = new int[5];
//		
//		//What is a baseline for this?
//		// font = 50
//		// para = font * 5/3
//		// nl = font
//		// factor = factor-1
//		
//		switch(lines) {
//		case 11:
//			props[0]=73;//para
//			props[1]=44;//nl
//			props[2]=44;//font
//			props[3]=36; //maxchars
//			props[4]=14;//factor
//
//		case 12:
//			props[0]=70;//para
//			props[1]=40;//nl
//			props[2]=41;//font
//			props[3]=38; //maxchars
//			props[4]=14;//factor
//
//			break;
//		case 13:
//			props[0]=69;//para
//			props[1]=40;//nl
//			props[2]=40;//font
//			props[3]=40; //maxchars
//			props[4]=13;//factor
//
//			break;
//		case 14:
//			props[0]=66;//para
//			props[1]=38;//nl
//			props[2]=39;//font
//			props[3]=42; //maxchars
//			props[4]=12;//factor
//
//			break;
//		case 15:
//			props[0]=62;//para
//			props[1]=35;//nl
//			props[2]=38;//font
//			props[3]=44;//maxchars
//			props[4]=11;//factor
//
//			break;
//		case 16:
//			props[0]=59;//para
//			props[1]=33;//nl
//			props[2]=37;//font
//			props[3]=46;//maxchars
//			props[4]=10;//factor
//
//			break;
//		case 17:
//			props[0]=0;//para
//			props[1]=0;//nl
//			props[2]=36;//font
//			props[3]=48;//maxchars
//			props[4]=9;//factor
//
//			break;
//		case 18:
//			props[0]=0;//para
//			props[1]=0;//nl
//			props[2]=35;//font
//			props[3]=50;//maxchars
//			props[4]=9;//factor
//
//			break;
//		case 19:
//			props[0]=0;//para
//			props[1]=0;//nl
//			props[2]=34;//font
//			props[3]=52;//maxchars
//			break;
//		case 20:
//			props[0]=0;//para
//			props[1]=0;//nl
//			props[2]=33;//font
//			props[3]=54;//maxchars
//			break;
//		default:
//			props[0]=75;//para
//			props[1]=45;//nl
//			props[2]=45;//font
//			props[3]=34;//maxchars
//			props[4]=15;//factor
//			break;
//		}
//		return props;
//	}
//	
//	
//}