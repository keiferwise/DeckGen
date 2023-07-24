package com.kif.deckgen.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kif.deckgenmodels.Card;
import com.kif.deckgenmodels.Deck;

@Component
public class DeckRowMapper implements RowMapper<Deck> {

	@Autowired
	CardDao cardDao;
	
	//@Autowired
	//CardRowMapper cardRowMapper;
	
	@Override
	public Deck mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Deck deck = new Deck();
		//
		deck.setDeckId(rs.getString("deck_id"));
		deck.setStatus(rs.getString("job_status"));
		deck.setName(rs.getString("deck_name"));
		
		List<Card> cards = new ArrayList<Card>();
		cards = cardDao.findAllByDeckId(rs.getString("deck_id"));
		deck.setCards(cards);
		
		//deck.setCards(null); // We need to call the dao and row mapper for the cards
		//
		
		return deck;
		
		
	}
	
	

}
