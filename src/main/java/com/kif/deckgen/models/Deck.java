package com.kif.deckgen.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
@Entity
public class Deck {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long deckId;
	


	@JsonProperty("cards")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
	//@JoinColumn(name = "deckId")
	private List<Card> cards = new ArrayList<Card>();
	
	private String status = "NEW";
	
	
	
	// Setters and Getters
	public List<Card> getCards() {
		return cards;
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
	public Long getDeckId() {
		return deckId;
	}

	public void setDeckId(Long deckId) {
		this.deckId = deckId;
	}
	

	
}
