package com.kif.deckgen.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Card {

	//@ManyToOne(targetEntity = Deck.class)
   //@JoinColumn(name = "deckId")
	//private Long deckId;
	
    @JsonProperty("name")
	private String name;
    @JsonProperty("mana_cost")
	private String manaCost;
    @JsonProperty("art_description")
	private String artDescription;
    @JsonProperty("types")
	private ArrayList<String> types;
    @JsonProperty("subtypes")
	private ArrayList<String> subtypes;
    @JsonProperty("rarity")
	private String rarity;
    @JsonProperty("rules-text")
	private String rulesText;
    @JsonProperty("flavor-text")
	private String flavorText;
    @JsonProperty("power")
	private String power;
    @JsonProperty("toughness")
	private String toughness;
    @JsonProperty("artist")
	private String artist;
    @JsonProperty("copyright")
	private String copyright;
    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long cardId;
	
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	/*
	public Long getDeckId() {
		return deckId;
	}
	public void setDeckId(Long deckId) {
		this.deckId = deckId;
	}
	*/
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
	public ArrayList<String> getTypes() {
		return types;
	}
	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}
	public ArrayList<String> getSubtypes() {
		return subtypes;
	}
	public void setSubtypes(ArrayList<String> subtypes) {
		this.subtypes = subtypes;
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
				+ types + ", subtypes=" + subtypes + ", rarity=" + rarity + ", rulesText=" + rulesText + ", flavorText="
				+ flavorText + ", power=" + power + ", toughness=" + toughness + ", artist=" + artist + ", copyright="
				+ copyright + ", cardId=" + cardId + "]";
	}
    
    
    
    
	

}
