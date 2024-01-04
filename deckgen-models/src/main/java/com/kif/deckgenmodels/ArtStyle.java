package com.kif.deckgenmodels;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class ArtStyle {
	
	@JsonProperty("short_name")
	private String shortName;
	@JsonProperty("description")
	private String description;
	

	public ArtStyle() {
		// TODO Auto-generated constructor stub
	}


	public String getShortName() {
		return shortName;
	}


	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public String toString() {
		return "ArtStyle [shortName=" + shortName + ", description=" + description + "]";
	}
	

}
