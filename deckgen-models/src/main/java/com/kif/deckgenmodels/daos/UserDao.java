package com.kif.deckgenmodels.daos;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.kif.deckgenmodels.User;

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
		//ArrayList<User> users = new ArrayList<User>();
		String sql = "select * from users where username = ?";
		
		//result = jdbcTemplate.execute();
		return 	jdbcTemplate.query(sql,userRowMapper,username).get(0);

	}
	
	public String save(String username,String password, String deckId,String email,boolean admin,String name,String role) {
		int result=0;
		
		String encodedPassword = new BCryptPasswordEncoder().encode(password);
		String userId = UUID.randomUUID().toString();
		result = jdbcTemplate.update(
                "insert into users (user_id,username,email,admin,name,password,ROLE) values(?,?,?,?,?,?,?)",
                userId,username,email,admin,name,encodedPassword,role);	
		
		return userId;
	}
	public List<User> allUsers(){
		String sql = "select * from users";
		return 	jdbcTemplate.query(sql,userRowMapper);		
	}
}
