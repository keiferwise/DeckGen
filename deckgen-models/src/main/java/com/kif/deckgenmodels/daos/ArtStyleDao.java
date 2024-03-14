package com.kif.deckgenmodels.daos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kif.deckgenmodels.ArtStyle;

@Repository
public class ArtStyleDao {
	@Autowired 
	JdbcTemplate jdbcTemplate;
	@Autowired
	ArtStyleRowMapper artStyleMapper; 

	public ArtStyleDao() {
		// TODO Auto-generated constructor stub
	}
	public List<ArtStyle> findAll() {
		List<ArtStyle> artStyle = new ArrayList<ArtStyle>();
        String sql = "SELECT * FROM art_style";
        
        artStyle = jdbcTemplate.query(sql, artStyleMapper);
        
		
		return artStyle;
	}

}
