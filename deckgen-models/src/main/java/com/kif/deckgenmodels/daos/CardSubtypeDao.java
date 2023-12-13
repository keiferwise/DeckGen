package com.kif.deckgenmodels.daos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kif.deckgenmodels.CardSubtype;

@Repository
public class CardSubtypeDao {
	@Autowired 
	JdbcTemplate jdbcTemplate;
	@Autowired
	CardSubtypeRowMapper cardSubtypeRowMapper; 
	
	public CardSubtypeDao() {
		// TODO Auto-generated constructor stub
	}
	
	public List<CardSubtype> getCardTypes(){
		List<CardSubtype> cardSubtypes = new ArrayList<CardSubtype>();
        String sql = "SELECT * FROM card_subtype";

        
        cardSubtypes = jdbcTemplate.query(sql, cardSubtypeRowMapper);
		
		System.out.println();
		return cardSubtypes;
	}

	
	
}
