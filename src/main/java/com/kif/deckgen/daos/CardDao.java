package com.kif.deckgen.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.kif.deckgen.models.Card;

@Repository
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
	
	public List<Card> findAllByDeckId(String cardId){
		
		List<Card> cards = new ArrayList<Card>();
        String sql = "SELECT * FROM card WHERE deck_id = ?";
        
        cards = jdbcTemplate.query(sql, cardRowMapper,cardId);
        //System.out.println(cards.isEmpty());
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
	public int save(Card card, String deckId) {
		int result=0;
		result = jdbcTemplate.update(
                "insert into card (card_id, card_name, mana_cost,"
                + "art_description,card_type,card_subtype,rarity,"
                + "rules_text,flavor_text,power,toughness,artist,copyright,deck_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                
                UUID.randomUUID().toString(), card.getName(), card.getManaCost(),card.getArtDescription(),card.getType(),card.getSubtype(),
                card.getRarity(),card.getRulesText(),card.getFlavorText(),card.getPower(),card.getToughness(),
                card.getArtist(),card.getCopyright(),
                deckId);	
		
		return result;
	}
	
	public Card getCardById(String cardId) {
		  String sql = "SELECT * FROM card WHERE card_id = ?";
		  Card deck = jdbcTemplate.queryForObject(sql,  new CardRowMapper(),new Object[]{cardId});
		  return deck;
		}
	
	
	public int updateCard(Card card,String cardId) {
		String sql = "UPDATE card SET 				card_name = ?,	 mana_cost = ?, 	art_description = ?, 	 card_type = ?,  card_subtype =?, 	rarity = ?, 	  rules_text = ?, 		flavor_text = ?,	 power = ?, 	  toughness = ? WHERE card_id=?";
		int rowsUpdated = jdbcTemplate.update(sql, card.getName(), card.getManaCost(), card.getArtDescription(), card.getType(), card.getSubtype(), card.getRarity(), card.getRulesText(), card.getFlavorText(), card.getPower(), card.getToughness(), cardId);
		return rowsUpdated;
	}
	
}
