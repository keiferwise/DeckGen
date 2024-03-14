package com.kif.deckgenmodels.daos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kif.deckgenmodels.CardType;
@Repository
public class CardTypeDao {
	@Autowired 
	JdbcTemplate jdbcTemplate;
	@Autowired
	CardTypeRowMapper cardTypeRowMapper; 
	public CardTypeDao() {
		// TODO Auto-generated constructor stub
	}
	
	public List<CardType> getCardTypes(){
		List<CardType> cardTypes = new ArrayList<CardType>();
        String sql = "SELECT * FROM card_type";

        
        cardTypes = jdbcTemplate.query(sql, cardTypeRowMapper);
		
		System.out.println();
		return cardTypes;
	}

}
