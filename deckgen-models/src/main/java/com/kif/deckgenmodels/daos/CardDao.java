package com.kif.deckgenmodels.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.kif.deckgenmodels.Card;

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
	
	/*
	 * Update this for multideck
	 * 		select * from card c inner join card_deck d on c.card_id = d.card_id where d.deck_id = ? 

	 */
	public List<Card> findAllByDeckId(String cardId){
		
		List<Card> cards = new ArrayList<Card>();
        String sql = "SELECT * FROM card WHERE deck_id = ?";
        
        cards = jdbcTemplate.query(sql, cardRowMapper,cardId);
        //System.out.println(cards.isEmpty());
		return cards;
		
	}
	public List<Card> nineRandom(){
		
		List<Card> cards = new ArrayList<Card>();
        String sql = "SELECT * FROM card ORDER BY RAND() LIMIT 9";
        
        cards = jdbcTemplate.query(sql, cardRowMapper);
        //System.out.println(cards.isEmpty());
		return cards;
		
	}
	
	/*
	 * Update this for multideck
	 * 
	 *  "insert into card_deck (card_id,deck_id,id) values (?,?,?)",
	 *  UUID.randomUUID().toString, 
	 *  myUUID.toString, 
	 *  UUID.randomUUID().toString
	 */
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
	/*
	 * Update this for multideck
	 */
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
	/*
	 * Update this for multideck
	 *  "insert into card_deck (card_id,deck_id,id) values (?,?,?)",
	 *  cardId, 
	 *  deckId, 
	 *  UUID.randomUUID().toString
	 */
	public int save(Card card, String deckId,String cardId) {
		int result=0;
		result = jdbcTemplate.update(
                "insert into card (card_id, card_name, mana_cost,"
                + "art_description,card_type,card_subtype,rarity,"
                + "rules_text,flavor_text,power,toughness,artist,copyright,deck_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                
                cardId, card.getName(), card.getManaCost(),card.getArtDescription(),card.getType(),card.getSubtype(),
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

	public int updateStatusInProgress(String cardId) {
		return updateStatus(cardId,"INPROGRESS");
	}
	public int updateStatusComplete(String cardId) {
		return updateStatus(cardId,"COMPLETE");
	}
	public int updateStatusFailed(String cardId) {
		return updateStatus(cardId,"FAILED");
	}
	public int updateStatusNew(String cardId) {
		return updateStatus(cardId,"NEW");
	}
	
	
	public int updateStatus(String cardId,String status) {
		
		String sql = "update card set status=? where card_id=?";
		int result = jdbcTemplate.update(
                sql,
                status,cardId);
		
		return result;
	}
	
}
