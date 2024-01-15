package com.kif.deckgen.utilities;

import org.springframework.stereotype.Service;

import com.kif.deckgenmodels.DeckIdea;


@Service
public class ManaCostUtility {

	public ManaCostUtility() {
		// TODO Auto-generated constructor stub
	}

	public boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public int[] orderManaCost(String manaCost) {
		int[] manaArray = new int[6];
		for (char c : manaCost.toCharArray()) {
			if (isNumeric(String.valueOf(c))) {

				manaArray[0] = Integer.parseInt(manaCost.replaceAll("[WUBRGwbrg{}]+", ""));
			} else {
				switch (c) {
				case 'W':
					manaArray[1] += 1;
					break;
				case 'U':
					manaArray[2] += 1;
					break;
				case 'B':
					manaArray[3] += 1;
					break;
				case 'R':
					manaArray[4] += 1;
					break;
				case 'G':
					manaArray[5] += 1;
					break;
				default:
					break;
				}
			}
		}

		return manaArray;
	}
	
	private String manaColour(DeckIdea idea) {
		
		String result = " ";
		if(idea.isBlack()) {result = result + "Black, ";};
		if(idea.isRed()) {result = result + "Red, ";};
		if(idea.isGreen()) {result = result + "Green, ";};
		if(idea.isWhite()) {result = result + "White, ";};
		if(idea.isBlue()) {result = result + "Blue, ";};
		
		result = result.substring(0, result.length()-2);
		
		System.out.println(result);
		
		
		return result;
	}
	private String getDeckColours(DeckIdea idea) {
		String cardColour="";
		//String path = "D:\\deckgen\\src\\main\\resources\\images\\";
		int colourCounter=0;
		//Get Colour Identity String
		if(idea.isWhite()) {cardColour+="white, "; colourCounter++;}
		if(idea.isBlue()) {cardColour+="blue, "; colourCounter++;}
		if(idea.isBlack()) {cardColour+="black, "; colourCounter++;}
		if(idea.isRed()) {cardColour+="red, "; colourCounter++;}
		if(idea.isGreen()) {cardColour+="green, "; colourCounter++;}


		//if(colourCounter>1) {
		//	cardColour = "Multicolour";
		//}
		else if(colourCounter == 0) {
			cardColour="Colourless, ";
		}
		//path = path + frameColour + ".png";

		//System.out.println(path);
		return cardColour.substring(0, cardColour.length()-2);
	}
}
