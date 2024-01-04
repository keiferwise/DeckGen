package com.kif.deckgenmodels.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kif.deckgenmodels.ArtStyle;
import com.kif.deckgenmodels.Card;

@Component
public class ArtStyleRowMapper implements RowMapper<ArtStyle> {

	public ArtStyleRowMapper() {
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public ArtStyle mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArtStyle as = new ArtStyle();
		
		as.setShortName(rs.getString(1));
		as.setShortName(rs.getString(2));
		
		return as;
	}
	
}
