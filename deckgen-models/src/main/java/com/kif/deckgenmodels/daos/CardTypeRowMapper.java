package com.kif.deckgenmodels.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kif.deckgenmodels.CardType;
@Component
public class CardTypeRowMapper implements RowMapper<CardType> {

	public CardTypeRowMapper()   {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CardType mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CardType cardType = new CardType();
		cardType.setType_id(rs.getString("type_id"));
		cardType.setType_name(rs.getString("type_name"));
		return cardType;
	}
}
