package com.kif.deckgen.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	
	public int saveAll( List<Card> list, UUID myUuid) {
		int result=0;
		for (Card card : list) {
			result = jdbcTemplate.update(
	                "insert into card (card_id, card_name, mana_cost,"
	                + "art_description,card_type,card_subtype,rarity,"
	                + "rules_text,flavor_text,power,toughness,artist,copyright,deck_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
	                
	                UUID.randomUUID().toString(), card.getName(), card.getManaCost(),card.getArtDescription(),card.getType(),card.getSubtype(),
	                card.getRarity(),card.getRulesText(),card.getFlavorText(),card.getPower(),card.getToughness(),
	                card.getArtist(),card.getCopyright(),
	                myUuid.toString());	
		}
		return result;
	}
	
	//TODO Select Card names and IDs for a particular deck and output a list
	
	//TODO Select Cards based on deck ID into a list of CARD
	
}
