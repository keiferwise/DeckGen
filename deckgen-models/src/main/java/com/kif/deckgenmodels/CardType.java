package com.kif.deckgenmodels;

public class CardType {
	
	String type_id;

	String type_name;

	public CardType() {
		// TODO Auto-generated constructor stub
	}
	public CardType(String type_id, String type_name) {
		super();
		this.type_id = type_id;
		this.type_name = type_name;
	}
	
	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	@Override
	public String toString() {
		return "Type [type_id=" + type_id + ", type_name=" + type_name + "]";
	}


}
