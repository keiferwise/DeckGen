package com.kif.deckgenmodels.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                "insert into deck (deck_name, job_status, deck_id, user_id) values(?,?,?,?)",
                deck.getName(), deck.getStatus(), deck.getDeckId(),deck.getUser_id());
		return result;
	}
	
	public String insertDefaultDeck(String userId) {
		String deckId = UUID.randomUUID().toString();
		String sql = "insert into deck (deck_name, job_status, deck_id, user_id) values(?,?,?,?)";
		int result = jdbcTemplate.update(
                sql,
                "Collection","COMPLETE",deckId,userId);
		return deckId;
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
	public List<Deck> findDecksByUserId(String userId) {
		
		List<Deck> decks = new ArrayList<Deck>();
		
        String sql = "SELECT * FROM deck where user_id = ?";
        
        decks = jdbcTemplate.query(sql, deckRowMapper,userId);
		
		return decks;
	}
	
	public int updateStatusInProgress(String deckId) {
		return updateStatus(deckId,"INPROGRESS");
	}
	public int updateStatusComplete(String deckId) {
		return updateStatus(deckId,"COMPLETE");
	}
	public int updateStatusFailed(String deckId) {
		return updateStatus(deckId,"FAILED");
	}
	public int updateStatusNew(String deckId) {
		return updateStatus(deckId,"NEW");
	}
	
	
	public int updateStatus(String deckId,String status) {
		
		String sql = "update table deck set status=? where deckId=?";
		int result = jdbcTemplate.update(
                sql,
                status,deckId);
		
		return result;
	}

}
