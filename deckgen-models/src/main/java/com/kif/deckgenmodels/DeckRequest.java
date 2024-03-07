package com.kif.deckgenmodels;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeckRequest {
	@JsonProperty("deckIdeaId")
	String deckIdeaId;
	@JsonProperty("deckId")
	String deckId;
	@JsonProperty("key")
	String key;
	
	public String getDeckIdeaId() {
		return deckIdeaId;
	}
	public void setDeckIdeaId(String deckIdeaId) {
		this.deckIdeaId = deckIdeaId;
	}
	public String getDeckId() {
		return deckId;
	}
	public void setDeckId(String deckId) {
		this.deckId = deckId;
	}
	public DeckRequest() {
		// TODO Auto-generated constructor stub
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public DeckRequest(String deckIdeaId, String deckId, String key) {
		super();
		this.deckIdeaId = deckIdeaId;
		this.deckId = deckId;
		this.key = key;
	}

	
	

}
