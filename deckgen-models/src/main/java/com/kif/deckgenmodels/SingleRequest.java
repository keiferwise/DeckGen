package com.kif.deckgenmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SingleRequest {
	@JsonProperty("name")
	private String name; 
	@JsonProperty("type")
	private String type; 
	@JsonProperty("theme")
	private String theme;
	@JsonProperty("artStyle")
	private String artStyle;
	@JsonProperty("vibe")
	private String vibe; 
	@JsonProperty("mana")
	private String mana;
	@JsonProperty("deckId")
	private String deckId;
	
	public SingleRequest() {

	
	
	}
	


	public SingleRequest(String name, String type, String theme, String artStyle, String vibe, String mana,
			String deckId) {
		super();
		this.name = name;
		this.type = type;
		this.theme = theme;
		this.artStyle = artStyle;
		this.vibe = vibe;
		this.mana = mana;
		this.deckId = deckId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getArtStyle() {
		return artStyle;
	}
	public void setArtStyle(String artStyle) {
		this.artStyle = artStyle;
	}
	public String getVibe() {
		return vibe;
	}
	public void setVibe(String vibe) {
		this.vibe = vibe;
	}
	public String getMana() {
		return mana;
	}
	public void setMana(String mana) {
		this.mana = mana;
	}

}
