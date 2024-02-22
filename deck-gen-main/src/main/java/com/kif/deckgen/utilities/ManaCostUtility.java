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
		if (idea.isBlack()) {
			result = result + "Black, ";
		}
		;
		if (idea.isRed()) {
			result = result + "Red, ";
		}
		;
		if (idea.isGreen()) {
			result = result + "Green, ";
		}
		;
		if (idea.isWhite()) {
			result = result + "White, ";
		}
		;
		if (idea.isBlue()) {
			result = result + "Blue, ";
		}
		;

		result = result.substring(0, result.length() - 2);

		System.out.println(result);

		return result;
	}

	private String getDeckColours(DeckIdea idea) {
		String cardColour = "";
		// String path = "D:\\deckgen\\src\\main\\resources\\images\\";
		int colourCounter = 0;
		// Get Colour Identity String
		if (idea.isWhite()) {
			cardColour += "white, ";
			colourCounter++;
		}
		if (idea.isBlue()) {
			cardColour += "blue, ";
			colourCounter++;
		}
		if (idea.isBlack()) {
			cardColour += "black, ";
			colourCounter++;
		}
		if (idea.isRed()) {
			cardColour += "red, ";
			colourCounter++;
		}
		if (idea.isGreen()) {
			cardColour += "green, ";
			colourCounter++;
		}

		// if(colourCounter>1) {
		// cardColour = "Multicolour";
		// }
		else if (colourCounter == 0) {
			cardColour = "Colourless, ";
		}
		// path = path + frameColour + ".png";

		// System.out.println(path);
		return cardColour.substring(0, cardColour.length() - 2);
	}

	public String manaCostForTemplate(String size, String rulesText) {

		String rt = rulesText.replaceAll("\\{W\\}", "<img class=\"mana-icon\" src=\"/images/w.png\"/>");
		rt = rt.replaceAll("\\{U\\}", "<img class=\"mana-icon\"src=\"/images/u.png\"/>");
		rt = rt.replaceAll("\\{u\\}", "<img class=\"mana-icon\"src=\"/images/u.png\"/>");

		rt = rt.replaceAll("\\{T\\}", "<img class=\"mana-icon\"src=\"/images/tap.png\"/>");
		rt = rt.replaceAll("\\{t\\}", "<img class=\"mana-icon\"src=\"/images/tap.png\"/>");

		rt = rt.replaceAll("\\{Tap\\}", "<img class=\"mana-icon\"src=\"/images/2.png\"/>");
		rt = rt.replaceAll("\\{TAP\\}", "<img class=\"mana-icon\"src=\"/images/2.png\"/>");
		rt = rt.replaceAll("\\{C\\}", "<img class=\"mana-icon\"src=\"/images/1.png\"/>");
		rt = rt.replaceAll("\\{B\\}", "<img class=\"mana-icon\"src=\"/images/b.png\"/>");
		rt = rt.replaceAll("\\{W\\}", "<img class=\"mana-icon\"src=\"/images/w.png\"/>");
		rt = rt.replaceAll("\\{X\\}", "<img class=\"mana-icon\"src=\"/images/x.png\"/>");
		rt = rt.replaceAll("\\{R\\}", "<img class=\"mana-icon\"src=\"/images/r.png\"/>");
		rt = rt.replaceAll("\\{G\\}", "<img class=\"mana-icon\"src=\"/images/g.png\"/>");
		rt = rt.replaceAll("\\{1\\}", "<img class=\"mana-icon\"src=\"/images/1.png\"/>");
		rt = rt.replaceAll("\\{2\\}", "<img class=\"mana-icon\"src=\"/images/2.png\"/>");
		rt = rt.replaceAll("\\{3\\}", "<img class=\"mana-icon\"src=\"/images/3.png\"/>");
		rt = rt.replaceAll("\\{4\\}", "<img class=\"mana-icon\"src=\"/images/4.png\"/>");
		rt = rt.replaceAll("\\{5\\}", "<img class=\"mana-icon\"src=\"/images/5.png\"/>");
		rt = rt.replaceAll("\\{6\\}", "<img class=\"mana-icon\"src=\"/images/6.png\"/>");
		rt = rt.replaceAll("\\{7\\}", "<img class=\"mana-icon\"src=\"/images/7.png\"/>");
		rt = rt.replaceAll("\\{8\\}", "<img class=\"mana-icon\"src=\"/images/8.png\"/>");
		rt = rt.replaceAll("\\{9\\}", "<img class=\"mana-icon\"src=\"/images/9.png\"/>");
		rt = rt.replaceAll("\\{10\\}", "<img class=\"mana-icon\"src=\"/images/10.png\"/>");

		if (size.equals("small")) {
			rt = rt.replaceAll("mana-icon", "mana-icon-small");
		} else if (size.equals("large")) {
			rt = rt.replaceAll("mana-icon", "mana-icon-large");
		}
		return rt;
	}
}