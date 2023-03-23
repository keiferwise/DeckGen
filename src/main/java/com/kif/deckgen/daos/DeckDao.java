package com.kif.deckgen.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.kif.deckgen.models.Deck;

@Component
public class DeckDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public int save(Deck deck) {
		
		int result = jdbcTemplate.update(
                "insert into deck (deck_name, status, deck_id) values(?,?,?)",
                deck.getName(), deck.getStatus(), deck.getDeckId());
		return result;
	}

}
