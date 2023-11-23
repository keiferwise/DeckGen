package com.kif.deckgenmodels.daos;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kif.deckgenmodels.Card;

public class UserDao {

	@Autowired 
	JdbcTemplate jdbcTemplate;
	
	
	public UserDao() {
		// TODO Auto-generated constructor stub
	}

	
	public int save(String username,String password, String deckId) {
		int result=0;
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String EncodedPassword = new BCryptPasswordEncoder().encode(password);
		
		result = jdbcTemplate.update(
                "insert into users (user_id,username,email,admin,name,password) values(?,?,?,?,?,?)",
                UUID.randomUUID().toString());	
		
		return result;
	}
}
