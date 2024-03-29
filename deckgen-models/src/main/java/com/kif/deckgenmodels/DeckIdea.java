package com.kif.deckgenmodels;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
@Component
public class DeckIdea {
	
	// The Theme
	@JsonProperty("theme")
	private String Theme;
	
	//Let the user define the name of a Legendary Creature that will be in the deck
	@JsonProperty("legends")
	private String Legends;
	
	//Allow the user to choose preferred colours
	@JsonProperty("red")
	private boolean red;
	@JsonProperty("green")
	private boolean green;
	@JsonProperty("black")
	private boolean black;
	@JsonProperty("blue")
	private boolean blue;
	@JsonProperty("white")
	private boolean white;
	@JsonProperty("deck-id")
	private String deckId;
	@JsonProperty("deck-idea-id")
	private String deckIdeaId;
	@JsonProperty("vibe")
	private String vibe;
	@JsonProperty("art-style")
	private String artStyle;
	
	

	public DeckIdea() {
		super();
	}

	
	public DeckIdea(String theme, String legends, boolean red, boolean green, boolean black, boolean blue,
			boolean white, String deckId, String deckIdeaId, String vibe, String artStyle) {
		super();
		Theme = theme;
		Legends = legends;
		this.red = red;
		this.green = green;
		this.black = black;
		this.blue = blue;
		this.white = white;
		this.deckId = deckId;
		this.deckIdeaId = deckIdeaId;
		this.vibe = vibe;
		this.artStyle = artStyle;
	}


	public String getArtStyle() {
		return artStyle;
	}


	public void setArtStyle(String artStyle) {
		this.artStyle = artStyle;
	}


	public String getDeckId() {
		return deckId;
	}
	public void setDeckId(String deckId) {
		this.deckId = deckId;
	}
	public String getDeckIdeaId() {
		return deckIdeaId;
	}
	public void setDeckIdeaId(String deckIdeaId) {
		this.deckIdeaId = deckIdeaId;
	}
	
	public String getTheme() {
		return Theme;
	}
	public void setTheme(String theme) {
		Theme = theme;
	}
	public String getLegends() {
		return Legends;
	}
	public void setLegends(String legends) {
		Legends = legends;
	}
	public boolean isRed() {
		return red;
	}
	public void setRed(boolean red) {
		this.red = red;
	}
	public boolean isGreen() {
		return green;
	}
	public void setGreen(boolean green) {
		this.green = green;
	}
	public boolean isBlack() {
		return black;
	}
	public void setBlack(boolean black) {
		this.black = black;
	}
	public boolean isBlue() {
		return blue;
	}
	public void setBlue(boolean blue) {
		this.blue = blue;
	}
	public boolean isWhite() {
		return white;
	}
	public void setWhite(boolean white) {
		this.white = white;
	}


	public String getVibe() {
		return vibe;
	}


	public void setVibe(String vibe) {
		this.vibe = vibe;
	}

}
