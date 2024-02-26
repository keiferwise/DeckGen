package com.kif.deckgenmodels;

public class CardDeck {

	private String cardId;
	
	private String deckId;
	
	private String id;
	
	public CardDeck() {
		// TODO Auto-generated constructor stub
		
		
	}

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CardDeck [cardId=" + cardId + ", deckId=" + deckId + ", id=" + id + "]";
	}
	

}
