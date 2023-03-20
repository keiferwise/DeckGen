package com.kif.deckgen.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public class Deck {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long deckId;
	
	@JsonProperty("cards")
	@OneToMany
	
	private ArrayList<Card> cards = new ArrayList<Card>();
	
	private String status = "NEW";
	
	// Setters and Getters
	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
