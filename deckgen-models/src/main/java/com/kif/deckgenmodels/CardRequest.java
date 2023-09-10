package com.kif.deckgenmodels;
import com.fasterxml.jackson.annotation.JsonProperty;
public class CardRequest {

	public CardRequest() {
		// TODO Auto-generated constructor stub
	}
	public CardRequest(String cardId, String theme, String deckId) {
		// TODO Auto-generated constructor stub
		this.cardId = cardId;
		this.theme=theme;
		this.deckIdeaId = deckId;
	}
	
	
	@JsonProperty("cardId")
	String cardId;
	@JsonProperty("theme")
	String theme;
	@JsonProperty("deckIdeaId")
	String deckIdeaId;
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
