package com.kif.deckgen.models;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class DeckIdea {
	
	// The Theme
	private String Theme;
	
	//Let the user define the name of a Legendary Creature that will be in the deck
	private ArrayList<String> Legends = new ArrayList<String>();
	
	//Allow the user to choose preferred colours
	private boolean red;
	private boolean green;
	private boolean black;
	private boolean blue;
	private boolean white;
	
	
	
	public DeckIdea() {
		super();
	}
	public DeckIdea(String theme, ArrayList<String> legends, boolean red, boolean green, boolean black, boolean blue,
			boolean white) {
		super();
		Theme = theme;
		Legends = legends;
		this.red = red;
		this.green = green;
		this.black = black;
		this.blue = blue;
		this.white = white;
	}
	public String getTheme() {
		return Theme;
	}
	public void setTheme(String theme) {
		Theme = theme;
	}
	public ArrayList<String> getLegends() {
		return Legends;
	}
	public void setLegends(ArrayList<String> legends) {
		Legends = legends;
	}
	public boolean isRed() {
		return red;
	}
	public void setRed(boolean red) {
		this.red = red;
	}
	public boolean isGreen() {
		return green;
	}
	public void setGreen(boolean green) {
		this.green = green;
	}
	public boolean isBlack() {
		return black;
	}
	public void setBlack(boolean black) {
		this.black = black;
	}
	public boolean isBlue() {
		return blue;
	}
	public void setBlue(boolean blue) {
		this.blue = blue;
	}
	public boolean isWhite() {
		return white;
	}
	public void setWhite(boolean white) {
		this.white = white;
	}

}
