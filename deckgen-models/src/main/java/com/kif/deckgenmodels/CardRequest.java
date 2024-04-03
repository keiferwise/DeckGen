package com.kif.deckgenmodels;
import com.fasterxml.jackson.annotation.JsonProperty;
public class CardRequest {

	public CardRequest() {
		// TODO Auto-generated constructor stub
	}
	

	
	
	public CardRequest(String cardId, String theme, String deckIdeaId, String key) {
		super();
		this.cardId = cardId;
		this.theme = theme;
		this.deckIdeaId = deckIdeaId;
		this.key = key;
	}




	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}




	@JsonProperty("cardId")
	String cardId;
	@JsonProperty("theme")
	String theme;
	@JsonProperty("deckIdeaId")
	String deckIdeaId;
	@JsonProperty("key")
	String key;
	
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getDeckIdeaId() {
		return deckIdeaId;
	}
	public void setDeckIdeaId(String deckIdeaId) {
		this.deckIdeaId = deckIdeaId;
	}
	
	

}