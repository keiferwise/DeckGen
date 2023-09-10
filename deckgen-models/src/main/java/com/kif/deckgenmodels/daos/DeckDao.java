package com.kif.deckgenmodels.daos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kif.deckgenmodels.Deck;

@Repository
public class DeckDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	DeckRowMapper deckRowMapper;
	
	public int save(Deck deck) {
		
		int result = jdbcTemplate.update(
                "insert into deck (deck_name, job_status, deck_id) values(?,?,?)",
                deck.getName(), deck.getStatus(), deck.getDeckId());
		return result;
	}
	
	public Deck findDeckById(String deckId) {
		List<Deck> decks = new ArrayList<Deck>();
        String sql = "SELECT * FROM deck WHERE deck_id = ?";
        
        decks = jdbcTemplate.query(sql, deckRowMapper,deckId);
        
		return decks.get(0);
	}
	
	public List<Deck> findAll() {
		List<Deck> decks = new ArrayList<Deck>();
        String sql = "SELECT * FROM deck";
        
        decks = jdbcTemplate.query(sql, deckRowMapper);
        
		
		return decks;
	}

}
