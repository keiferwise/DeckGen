package com.kif.deckgenmodels.daos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kif.deckgenmodels.DeckIdea;

@Repository
public class DeckIdeaDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	DeckIdeaRowMapper deckIdeaRowMapper;

	
	public DeckIdeaDao() {
		// TODO Auto-generated constructor stub
	}
	
	//deck_idea_id,legends,theme,black,blue,green,red,white,deck_id
	
	//Insert Deck Idea
	public int save(DeckIdea deckIdea) {
		
		int result = jdbcTemplate.update(
                "insert into deck_idea (deck_idea_id,legends,theme,black,blue,green,red,white,deck_id,vibe,art_style) values(?,?,?,?,?,?,?,?,?,?,?)",
                deckIdea.getDeckIdeaId(), deckIdea.getLegends(),deckIdea.getTheme(), deckIdea.isBlack(), deckIdea.isBlue(), deckIdea.isGreen(),deckIdea.isRed(),deckIdea.isWhite(), deckIdea.getDeckId(),deckIdea.getVibe(),deckIdea.getArtStyle());
		return result;
	}
	
	//Get Deck Idea by Deck_id
	public DeckIdea findByDeckId(String deckId) {
		List<DeckIdea> deckIdeas = new ArrayList<DeckIdea>();
        String sql = "SELECT * FROM deck_idea WHERE deck_id = ?";
        
        deckIdeas = jdbcTemplate.query(sql, deckIdeaRowMapper,deckId);
        
		return deckIdeas.get(0);
	}
	
	//Get Deck Idea by Deck_id
		public DeckIdea findByDeckIdeaId(String deckIdeaId) {
			List<DeckIdea> deckIdeas = new ArrayList<DeckIdea>();
	        String sql = "SELECT * FROM deck_idea WHERE deck_idea_id = ?";
	        
	        deckIdeas = jdbcTemplate.query(sql, deckIdeaRowMapper,deckIdeaId);
	        
			return deckIdeas.get(0);
		}
		
	
	

}
