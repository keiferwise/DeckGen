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
	
	

}
