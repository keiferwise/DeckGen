package com.kif.deckgenmodels.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kif.deckgenmodels.CardSubtype;

@Component
public class CardSubtypeRowMapper implements RowMapper<CardSubtype> {

	public CardSubtypeRowMapper() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CardSubtype mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CardSubtype cardSubtype = new CardSubtype();
		cardSubtype.setType_id(rs.getString("type_id"));
		cardSubtype.setSubtype_name(rs.getString("subtype_name"));
		cardSubtype.setSubtype_id(rs.getString("type_id"));

		return cardSubtype;
	
	}
}
