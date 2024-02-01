package com.kif.deckgenmodels.daos;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.kif.deckgenmodels.User;
import com.kif.deckgenmodels.Card;

@Repository
public class UserDao {

	@Autowired 
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	UserRowMapper userRowMapper;

	
	public UserDao() {
		// TODO Auto-generated constructor stub
	}

	public User findUserByName(String username) {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "select * from users where username = ?";
		
		//result = jdbcTemplate.execute();
		return 	jdbcTemplate.query(sql,userRowMapper,username).get(0);

	}
	
	public String save(String username,String password, String deckId,String email,boolean admin,String name) {
		int result=0;
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String encodedPassword = new BCryptPasswordEncoder().encode(password);
		String userId = UUID.randomUUID().toString();
		result = jdbcTemplate.update(
                "insert into users (user_id,username,email,admin,name,password) values(?,?,?,?,?,?)",
                userId,username,email,admin,name,encodedPassword);	
		
		return userId;
	}
}
