package com.kif.deckgenmodels;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class Deck {
	
	private String deckId;
	@JsonProperty("cards")
	private List<Card> cards = new ArrayList<Card>();
	private String name;
	private String status = "NEW";
	private String user_id;

	// Setters and Getters
	public List<Card> getCards() {
		return cards;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeckId() {
		return deckId;
	}

	public void setDeckId(String deckId) {
		this.deckId = deckId;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Deck(String deckId, List<Card> cards, String name, String status, String user_id) {
		super();
		this.deckId = deckId;
		this.cards = cards;
		this.name = name;
		this.status = status;
		this.user_id = user_id;
	}

	public Deck() {
		// TODO Auto-generated constructor stub
	}



	
	
}
