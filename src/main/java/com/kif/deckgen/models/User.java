package com.kif.deckgen.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class User {
	
	
	private String userId;
	
	private String userName;
	
	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private Character admin;
	
	private String password;
	
	//TODO Figure out Spring Security and integrate it.

}
