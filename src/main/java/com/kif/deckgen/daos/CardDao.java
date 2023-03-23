package com.kif.deckgen.daos;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.kif.deckgen.models.Card;

@Component
public class CardDao {

	@Autowired 
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	CardRowMapper cardRowMapper; 
	
	public Integer count(){
		int result = jdbcTemplate.queryForObject(
			    "SELECT (*) FROM card", Integer.class);
		
		System.out.println(result);
		return result;
	}
	
	public List<Card> findAllByDeckId(Long CardId){
		
		List<Card> cards = new ArrayList<Card>();
        String sql = "SELECT * FROM card WHERE card_id = ?";
        cards = jdbcTemplate.query(sql, cardRowMapper);
		return cards;
		
	}
	
	//TODO Select Card names and IDs for a particular deck and output a list
	
	//TODO Select Cards based on deck ID into a list of CARD
	
}
