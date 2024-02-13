package com.kif.deckgenmodels.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kif.deckgenmodels.Card;

@Component
public class CardRowMapper implements RowMapper<Card> {

	@Override
	public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Card card = new Card();
		card.setCardId(rs.getString("card_id"));
		card.setName(rs.getString("card_name"));
		card.setManaCost(rs.getString("mana_cost"));
		card.setArtDescription(rs.getString("art_description"));
		card.setType(rs.getString("card_type"));
		card.setSubtype(rs.getString("card_subtype"));
		card.setRarity(rs.getString("rarity"));
		card.setRulesText(rs.getString("rules_text"));
		card.setFlavorText(rs.getString("flavor_text"));
		card.setPower(rs.getString("power"));
		card.setToughness(rs.getString("toughness"));
		card.setArtist(rs.getString("artist"));
		card.setCopyright(rs.getString("copyright"));
		card.setStatus(rs.getString("status"));
		return card;

	}

}
