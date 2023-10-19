package com.kif.deckgenmodels;


import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class Card {
	@JsonProperty("deck_id")
	private String deckId;
    @JsonProperty("card_name")
	private String name;
    @JsonProperty("mana_cost")
	private String manaCost;
    @JsonProperty("art_description")
	private String artDescription;
    @JsonProperty("card_type")
	private String type;
    @JsonProperty("card_subtype")
	private String subtype;
    @JsonProperty("rarity")
	private String rarity;
    @JsonProperty("rules_text")
	private String rulesText;
    @JsonProperty("flavor_text")
	private String flavorText;
    @JsonProperty("power")
	private String power;
    @JsonProperty("toughness")
	private String toughness;
    @JsonProperty("artist")
	private String artist;
    @JsonProperty("copyright")
	private String copyright;
    
	private String cardId;
	
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public String getDeckId() {
		return deckId;
	}
	public void setDeckId(String deckId) {
		this.deckId = deckId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getManaCost() {
		return manaCost;
	}
	public void setManaCost(String manaCost) {
		this.manaCost = manaCost;
	}
	public String getArtDescription() {
		return artDescription;
	}
	public void setArtDescription(String artDescription) {
		this.artDescription = artDescription;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype= subtype;
	}
	public String getRarity() {
		return rarity;
	}
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}
	public String getRulesText() {
		return rulesText;
	}
	public void setRulesText(String rulesText) {
		this.rulesText = rulesText;
	}
	public String getFlavorText() {
		return flavorText;
	}
	public void setFlavorText(String flavorText) {
		this.flavorText = flavorText;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getToughness() {
		return toughness;
	}
	public void setToughness(String toughness) {
		this.toughness = toughness;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	@Override
	public String toString() {
		return "Card [name=" + name + ", manaCost=" + manaCost + ", artDescription=" + artDescription + ", types="
				+ type + ", subtypes=" + subtype + ", rarity=" + rarity + ", rulesText=" + rulesText + ", flavorText="
				+ flavorText + ", power=" + power + ", toughness=" + toughness + ", artist=" + artist + ", copyright="
				+ copyright + ", cardId=" + cardId + "]";
	}
    public String getRulesForTemplate(String size) {
    	
    	String rt = 
    			rulesText.replaceAll("\\{W\\}", "<img class=\"mana-icon\" src=\"/images/w.png\"/>");
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

    	 if(size.equals("small")) {
    		 rt=rt.replaceAll("mana-icon", "mana-icon-small");
    	 }
    	 else if(size.equals("large")) {
    		 rt= rt.replaceAll("mana-icon", "mana-icon-large");
    	 }
    	 System.out.println(rt);
    	 
    	 
    	return rt;
    }
    public String getTextSize(String cardSize) {
    	int maxChars=45;
		ArrayList<String> rulesArray = new ArrayList<String>();
		ArrayList<String> flavorArray =  new ArrayList<String>();
		rulesArray=removeBlankLines(rulesArray);
		flavorArray=removeBlankLines(flavorArray);
		
		int numLines =numberOfLines(rulesArray,maxChars)+numberOfLines(flavorArray,maxChars);
    	String fontSize="";
    	if(cardSize.equals("small")) {
    		if(numLines<9) {
    			fontSize="12.5";
    		}
    		else if (numLines==10) {
    			fontSize="11.5";

    		}
    		else if (numLines==11) {
    			fontSize="10.5";

    		}
    		else if (numLines==12) {
    			fontSize="9.5";

    		}
    		else {
    			fontSize="8.5";

    		}
    		
    	}
    	else if(cardSize.equals("mid")) {
    		if(numLines<9) {
    			fontSize="25px";
    		}
    		else if (numLines==10) {
    			fontSize="23";

    		}
    		else if (numLines==11) {
    			fontSize="21";

    		}
    		else if (numLines==12) {
    			fontSize="19";

    		}
    		else {
    			fontSize="17";

    		}
    	}
    	else if (cardSize.equals("large")) {
    		if(numLines<9) {
    			fontSize="50px";
    		}
    		else if (numLines==10) {
    			fontSize="46";

    		}
    		else if (numLines==11) {
    			fontSize="42";

    		}
    		else if (numLines==12) {
    			fontSize="38";

    		}
    		else {
    			fontSize="34";

    		}
    	}
    	
    	
    	return fontSize;
    }
    
    
	private int numberOfLines(ArrayList<String> textList,int charLimit) {

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
					lineCount+=45;
					length=0;
					//temp="";
				} 
			}

		}
		return (lineCount+(textList.size()*75))/45;

	}
	
	private ArrayList<String> removeBlankLines(ArrayList<String> lines) {
		for(int r=0;r<lines.size();r++) {
			if(lines.get(r).isBlank()) {
				lines.remove(r);
			}
		}
		return lines;
	}
}
