package com.kif.deckgen.models;


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
    public String getRulesForTemplate() {
    	
    	String rt = rulesText.replaceAll("\\{W\\}", "<img src=\"images/w.png\"/>");
    	 rt = rt.replaceAll("\\{U\\}", "<img class=\"mana-icon\"src=\"/images/u.png\"/>");
    	 rt = rt.replaceAll("\\{T\\}", "<img class=\"mana-icon\"src=\"/images/tap.png\"/>");
    	 rt = rt.replaceAll("\\{C\\}", "<img class=\"mana-icon\"src=\"/images/1.png\"/>");
    	 rt = rt.replaceAll("\\{W\\}", "<img class=\"mana-icon\"src=\"/images/w.png\"/>");
    	 rt = rt.replaceAll("\\{X\\}", "<img class=\"mana-icon\"src=\"/images/x.png\"/>");
    	 rt = rt.replaceAll("\\{R\\}", "<img class=\"mana-icon\"src=\"/images/r.png\"/>");
    	 rt = rt.replaceAll("\\{G\\}", "<img class=\"mana-icon\"src=\"/images/g.png\"/>");






    	return rt;
    }
    
    
    
	

}
