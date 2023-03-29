package com.kif.deckgen.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kif.deckgen.models.DeckIdea;

@Component
public class DeckIdeaRowMapper implements RowMapper<DeckIdea> {

		@Override
		public DeckIdea mapRow(ResultSet rs, int rowNum) throws SQLException {
			DeckIdea deckIdea = new DeckIdea();
			
			deckIdea.setTheme(rs.getString("theme"));
			deckIdea.setLegends(rs.getString("legends"));
			deckIdea.setDeckId(rs.getString("deck_id"));
			deckIdea.setRed(rs.getBoolean("red"));
			deckIdea.setGreen(rs.getBoolean("green"));
			deckIdea.setBlue(rs.getBoolean("blue"));
			deckIdea.setBlack(rs.getBoolean("black"));
			deckIdea.setWhite(rs.getBoolean("white"));
			deckIdea.setDeckIdeaId(rs.getString("deck_idea_id"));
			return deckIdea;
		
		}

		public DeckIdeaRowMapper() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		

	

}
