package com.kif.deckgenmodels;

public class CardSubtype {

	public CardSubtype() {
		// TODO Auto-generated constructor stub
	}
	
	private String type_id;
	private String subtype_name;
	private String subtype_id;
	
	
	@Override
	public String toString() {
		return "CardSubtype [type_id=" + type_id + ", subtype_name=" + subtype_name + ", subtype_id=" + subtype_id
				+ "]";
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getSubtype_name() {
		return subtype_name;
	}
	public void setSubtype_name(String subtype_name) {
		this.subtype_name = subtype_name;
	}
	public String getSubtype_id() {
		return subtype_id;
	}
	public void setSubtype_id(String subtype_id) {
		this.subtype_id = subtype_id;
	}
	
	
}
