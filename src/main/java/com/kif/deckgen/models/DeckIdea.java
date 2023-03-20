package com.kif.deckgen.models;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DeckIdea {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long deckIdeaId;
	private String Theme;
	
	//Let the user define the name of a Legendary Creature that will be in the deck
	private ArrayList<String> Legends = new ArrayList<String>();
	
	//Allow the user to choose preferd colours
	private boolean red;
	private boolean green;
	private boolean black;
	private boolean blue;
	private boolean white;

}
